package com.qingfeng.currency.authority.biz.service.auth.impl;

import cn.hutool.core.util.StrUtil;
import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.cms.domain.dict.vo.DictExcelVo;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.authority.biz.dao.auth.UserMapper;
import com.qingfeng.currency.authority.biz.service.auth.UserRoleService;
import com.qingfeng.currency.authority.biz.service.auth.UserService;
import com.qingfeng.currency.authority.biz.service.auth.exception.UserServiceExceptionMsg;
import com.qingfeng.currency.authority.biz.service.auth.listener.UserVoExcelListener;
import com.qingfeng.currency.authority.biz.service.core.OrgService;
import com.qingfeng.currency.authority.biz.service.core.StationService;
import com.qingfeng.currency.authority.biz.service.mq.producer.RabbitSendMsg;
import com.qingfeng.currency.authority.dto.auth.UserUpdatePasswordDTO;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.authority.entity.auth.UserRole;
import com.qingfeng.currency.authority.entity.auth.vo.UserVo;
import com.qingfeng.currency.authority.entity.core.Org;
import com.qingfeng.currency.authority.entity.core.Station;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.currency.utils.BizAssert;
import com.qingfeng.sdk.messagecontrol.StuInfo.StuInfoApi;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/17
 */
@Service
@Slf4j
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    public static final Integer SECONDARY_COLLEGE_ID = 102;

    @Autowired
    private UserRoleService userRoleService;
    @Autowired
    private OrgService orgService;
    @Autowired
    private StationService stationService;
    @Autowired
    private StuInfoApi stuInfoApi;
    @Autowired
    private RabbitSendMsg rabbitSendMsg;
    @Autowired
    private ObjectMapper objectMapper;

    @Override
    public IPage<User> findPage(IPage<User> page, LbqWrapper<User> wrapper) {
        return baseMapper.findPage(page, wrapper);
    }

    @Override
    public int resetPassErrorNum(Long id) {
        return baseMapper.resetPassErrorNum(id);
    }


    @Override
    public Boolean updatePassword(UserUpdatePasswordDTO data) {
        BizAssert.equals(data.getConfirmPassword(), data.getPassword(), "密码与确认密码不一致");

        User user = getById(data.getId());
        BizAssert.notNull(user, "用户不存在");
        String oldPassword = DigestUtils.md5Hex(data.getOldPassword());
        BizAssert.equals(user.getPassword(), oldPassword, "旧密码错误");

        User build = User.builder().password(data.getPassword()).id(data.getId()).build();
        this.updateUser(build);
        return true;
    }

    @Override
    public User getByAccount(String account) {
        return super.getOne(Wraps.<User>lbQ().eq(User::getAccount, account));
    }

    @Override
    public List<User> findUserByRoleId(Long roleId, String keyword) {
        return baseMapper.findUserByRoleId(roleId, keyword);
    }

    @Override
    public void updatePasswordErrorNumById(Long id) {
        baseMapper.incrPasswordErrorNumById(id);
    }

    @Override
    public void updateLoginTime(String account) {
        baseMapper.updateLastLoginTime(account, LocalDateTime.now());
    }

    @Override
    public User saveUser(User user) {
        // 永不过期
        user.setPasswordExpireTime(null);

        user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        user.setPasswordErrorNum(0);
        super.save(user);
        return user;
    }

    @Override
    public boolean reset(List<Long> ids) {
        LocalDateTime passwordExpireTime = null;
        //pinda123
        String defPassword = "cea87ef1cb2e47570020bf7c014e1074";
        super.update(Wraps.<User>lbU()
                .set(User::getPassword, defPassword)
                .set(User::getPasswordErrorNum, 0L)
                .set(User::getPasswordErrorLastTime, null)
                .set(User::getPasswordExpireTime, passwordExpireTime)
                .in(User::getId, ids)
        );
        return true;
    }

    @Override
    public User updateUser(User user) {
        // 永不过期
        user.setPasswordExpireTime(null);

        if (StrUtil.isNotEmpty(user.getPassword())) {
            user.setPassword(DigestUtils.md5Hex(user.getPassword()));
        }
        super.updateById(user);
        return user;
    }

    @Override
    public boolean remove(List<Long> ids) {
        userRoleService.remove(Wraps.<UserRole>lbQ()
                .in(UserRole::getUserId, ids)
        );
        return super.removeByIds(ids);
    }

    /**
     * 导出学生信息Excel模板
     *
     * @param response
     */
    @Override
    public void exportUserTemplate(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //设置URLEncoder.encode可以解决中文乱码问题   这个和EasyExcel没有关系
            response.setHeader("Content-Disposition",
                    "attachment;filename=" +
                            URLEncoder.encode("学生信息模板", "UTF-8") +
                            ".xlsx");

            // 调用方法实现写操作
            EasyExcel.write(response.getOutputStream(), UserVo.class)
                    .sheet("学生信息列表")
                    .doWrite(Collections.singletonList(
                            UserVo.builder()
                                    .account("使用学号（如：201910801001），学生必须保证唯一")
                                    .name("张三")
                                    .email("xxxxx@qq.com，没有可以不写")
                                    .mobile("13131313311，没有可以不写")
                                    .sex("男、女、未知")
                                    .status(Boolean.TRUE)
                                    .password("123456")
                                    .studentNum("201910801001")
                                    .birth(LocalDate.now())
                                    .nation("汉/汉族")
                                    .politicsStatus("群众/共青团员/中共党员")
                                    .enterTime(LocalDate.now())
                                    .graduateTime(LocalDate.now())
                                    .idCard("511121200008121095")
                                    .hukou("农村/城市")
                                    .qq("35317631")
                                    .weChat("13131313311")
                                    .nativePlace("四川攀枝花")
                                    .address("四川省攀枝花市炳草岗街道机场路十号攀枝花学院")
                                    .stateSchool("在籍在校/离校/辍学。。。")
                                    .type("本科/专科/研究生")
                                    .department("数学与计算机学院（大数据学院）")
                                    .major("计算机科学与技术")
                                    .grade("2019级")
                                    .clazz("1班")
                                    .educationalSystem("两年制、三年制、四年制、五年制")
                                    .hobyDes("个人描述，可以不写")
                                    .build()));
        } catch (Exception e) {
            throw new BizException(ExceptionCode.OPERATION_EX.getCode(), UserServiceExceptionMsg.EXPORT_TEMPLATE_FAILD.getMsg());
        }
    }

    /**
     * 导出学生信息Excel
     *
     * @param response
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void exportUser(HttpServletResponse response) {
        try {
            response.setContentType("application/vnd.ms-excel");
            response.setCharacterEncoding("utf-8");
            //设置URLEncoder.encode可以解决中文乱码问题   这个和EasyExcel没有关系
            response.setHeader("Content-Disposition",
                    "attachment;filename=" +
                            URLEncoder.encode("学生信息列表", "UTF-8") +
                            ".xlsx");

            //先查询二级学院的Id
            List<Long> orgIds = orgService.list(Wraps.lbQ(new Org())
                            .eq(Org::getParentId, SECONDARY_COLLEGE_ID))
                    .stream()
                    .map(Org::getId)
                    .collect(Collectors.toList());
            //查询二级学院对应的学生组织的Id（规则是二级学院下的职位只有负责人和学生两类）
            List<Long> stationIds = stationService.list(Wraps.lbQ(new Station())
                            .in(Station::getOrgId, orgIds)
                            .like(Station::getName, "学生"))
                    .stream()
                    .map(Station::getId)
                    .collect(Collectors.toList());
            //先查询所有符合条件的学生
            List<User> userList = baseMapper.selectList(Wraps.lbQ(new User())
                    .in(User::getStationId, stationIds));
            //根据学生信息去查询详情信息进行封装
            List<UserVo> userVoList = userList.stream().map(u ->
                            // 读取数据 采用异步处理  避免进行排队等候
                            CompletableFuture.supplyAsync(() -> {
                                        UserVo userVo = new UserVo();
                                        userVo.setAccount(u.getAccount())
                                                .setName(u.getName())
                                                .setEmail(u.getEmail())
                                                .setMobile(u.getMobile())
                                                .setSex(u.getSex().getDesc())
                                                .setStatus(u.getStatus())
                                                .setPassword(u.getPassword());
                                        //查询出用户对应的详细信息，然后进行封装
                                        StuInfoEntity stuInfoEntity = stuInfoApi.info(u.getId()).getData();
                                        Optional.ofNullable(stuInfoEntity)
                                                .ifPresent(s ->
                                                    userVo.setStudentNum(s.getStudentNum())
                                                            .setBirth(s.getBirth())
                                                            .setNation(s.getNation())
                                                            .setPoliticsStatus(s.getPoliticsStatus().getDesc())
                                                            .setEnterTime(s.getEnterTime())
                                                            .setGraduateTime(s.getGraduateTime())
                                                            .setIdCard(s.getIdCard())
                                                            .setHukou(s.getHukou().getDesc())
                                                            .setQq(s.getQq())
                                                            .setWeChat(s.getWeChat())
                                                            .setNativePlace(s.getNativePlace())
                                                            .setAddress(s.getAddress())
                                                            .setStateSchool(s.getStateSchool().getDesc())
                                                            .setType(s.getType().getDesc())
                                                            .setDepartment(s.getDepartment().getCode())
                                                            .setMajor(s.getMajor())
                                                            .setGrade(s.getGrade())
                                                            .setClazz(s.getClazz())
                                                            .setEducationalSystem(s.getEducationalSystem().getDesc())
                                                            .setHobyDes(s.getHobyDes())
                                                );
                                        /*if (ObjectUtil.isNotEmpty(stuInfoEntity)) {
                                            userVo.setStudentNum(stuInfoEntity.getStudentNum())
                                                    .setBirth(stuInfoEntity.getBirth())
                                                    .setNation(stuInfoEntity.getNation())
                                                    .setPoliticsStatus(stuInfoEntity.getPoliticsStatus().getDesc())
                                                    .setEnterTime(stuInfoEntity.getEnterTime())
                                                    .setGraduateTime(stuInfoEntity.getGraduateTime())
                                                    .setIdCard(stuInfoEntity.getIdCard())
                                                    .setHukou(stuInfoEntity.getHukou().getDesc())
                                                    .setQq(stuInfoEntity.getQq())
                                                    .setWeChat(stuInfoEntity.getWeChat())
                                                    .setNativePlace(stuInfoEntity.getNativePlace())
                                                    .setAddress(stuInfoEntity.getAddress())
                                                    .setStateSchool(stuInfoEntity.getStateSchool().getDesc())
                                                    .setType(stuInfoEntity.getType().getDesc())
                                                    .setDepartment(stuInfoEntity.getDepartment().getCode())
                                                    .setMajor(stuInfoEntity.getMajor())
                                                    .setGrade(stuInfoEntity.getGrade())
                                                    .setClazz(stuInfoEntity.getClazz())
                                                    .setEducationalSystem(stuInfoEntity.getEducationalSystem().getDesc())
                                                    .setHobyDes(stuInfoEntity.getHobyDes());
                                        }*/
                                        return userVo;

                                    }
                            ))
                    .collect(Collectors.toList())
                    .stream()
                    .map(CompletableFuture::join)
                    .collect(Collectors.toList());


            // 调用方法实现写操作
            EasyExcel.write(response.getOutputStream(), DictExcelVo.class)
                    .sheet("学生信息列表")
                    .doWrite(userVoList);
        } catch (Exception e) {
            throw new BizException(ExceptionCode.OPERATION_EX.getCode(), UserServiceExceptionMsg.EXPORT_FAILD.getMsg());
        }
    }

    /**
     * 学生信息Excel导入
     *
     * @param file
     * @param userId
     */
    @Override
    public void importUser(MultipartFile file) {
        System.out.println(file.getOriginalFilename());
        try {
            EasyExcel.read(file.getInputStream(), UserVo.class, new UserVoExcelListener(this, rabbitSendMsg, objectMapper)).sheet().doRead();
        } catch (IOException e) {
            throw new BizException(ExceptionCode.OPERATION_EX.getCode(), UserServiceExceptionMsg.IMPORT_FAILD.getMsg());
        }
    }
}
