package com.qingfeng.cms.biz.notice.service.impl;

import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.clazz.service.ClazzInfoService;
import com.qingfeng.cms.biz.college.service.CollegeInformationService;
import com.qingfeng.cms.biz.notice.dao.NoticeDao;
import com.qingfeng.cms.biz.notice.dao.UserNoticeCheckDao;
import com.qingfeng.cms.biz.notice.service.NoticeService;
import com.qingfeng.cms.biz.student.service.StuInfoService;
import com.qingfeng.cms.domain.clazz.entity.ClazzInfoEntity;
import com.qingfeng.cms.domain.college.entity.CollegeInformationEntity;
import com.qingfeng.cms.domain.dict.enums.DictDepartmentEnum;
import com.qingfeng.cms.domain.notice.dto.NoticeQueryDTO;
import com.qingfeng.cms.domain.notice.dto.NoticeSaveDTO;
import com.qingfeng.cms.domain.notice.dto.NoticeUpdateDTO;
import com.qingfeng.cms.domain.notice.entity.NoticeEntity;
import com.qingfeng.cms.domain.notice.entity.UserNoticeCheckEntity;
import com.qingfeng.cms.domain.notice.ro.NoticeRo;
import com.qingfeng.cms.domain.notice.vo.NoticePageVo;
import com.qingfeng.cms.domain.notice.vo.NoticeUserPageVo;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.authority.entity.auth.vo.UserRoleVo;
import com.qingfeng.currency.base.entity.SuperEntity;
import com.qingfeng.currency.common.enums.RoleEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.sdk.auth.role.RoleApi;
import com.qingfeng.sdk.auth.role.UserRoleApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 系统公告表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, NoticeEntity> implements NoticeService {

    @Autowired
    private UserNoticeCheckDao userNoticeCheckDao;
    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private UserRoleApi userRoleApi;
    @Autowired
    private CollegeInformationService collegeInformationService;
    @Autowired
    private StuInfoService stuInfoService;
    @Autowired
    private ClazzInfoService clazzInfoService;
    @Autowired
    private RoleApi roleApi;

    /**
     * 发布第二课堂公告
     *
     * @param noticeSaveDTO
     * @param userId
     */
    @Override
    public void publishNotice(NoticeSaveDTO noticeSaveDTO, Long userId) {
        UserRoleVo userRoleVo = userRoleApi.findRoleIdByUserId(userId).getData();
        NoticeEntity noticeEntity = dozerUtils.map2(noticeSaveDTO, NoticeEntity.class);
        noticeEntity.setUserId(userId)
                .setUserCode(userRoleVo.getCode())
                .setReadNum(0);

        baseMapper.insert(noticeEntity);
    }

    /**
     * 修改已发布的公告信息
     *
     * @param noticeUpdateDTO
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void updateNoticeById(NoticeUpdateDTO noticeUpdateDTO, Long userId) {
        NoticeEntity noticeEntity = dozerUtils.map2(noticeUpdateDTO, NoticeEntity.class);
        noticeEntity.setReadNum(0);

        // 进行修改
        baseMapper.updateById(noticeEntity);

        // 需要删除之前用户已经阅读过的记录
        userNoticeCheckDao.delete(
                Wraps.lbQ(new UserNoticeCheckEntity())
                        .eq(UserNoticeCheckEntity::getNoticeId, noticeEntity.getId())
        );

    }

    /**
     * 根据Id查询公告信息
     *
     * @param id
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public NoticeEntity getNoticeById(Long id, Long userId) {
        // 查询公告信息
        NoticeEntity noticeEntity = baseMapper.selectById(id);
        // 查看公告信息，需要看是否是本人查看，如果不是本人，那就需要记录用户的查看记录， 并且修改阅读量
        if (!noticeEntity.getUserId().equals(userId)) {
            noticeEntity.setReadNum(noticeEntity.getReadNum() + 1);
            baseMapper.updateById(noticeEntity);

            // 查询用户是否查看该条公告
            UserNoticeCheckEntity userNoticeCheckEntity = userNoticeCheckDao.selectOne(
                    Wraps.lbQ(new UserNoticeCheckEntity())
                            .eq(UserNoticeCheckEntity::getNoticeId, noticeEntity.getId())
                            .eq(UserNoticeCheckEntity::getUserId, userId)
            );
            if (ObjectUtil.isEmpty(userNoticeCheckEntity)) {
                // 保存用户查看记录
                userNoticeCheckDao.insert(
                        UserNoticeCheckEntity.builder()
                                .userId(userId)
                                .noticeId(noticeEntity.getId())
                                .build()
                );
            }
        }

        return noticeEntity;
    }

    /**
     * 根据Id删除第二课堂公告信息，连同用户查看记录一并删除
     *
     * @param id
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void deleteNoticeById(Long id) {
        baseMapper.deleteById(id);
        // 需要删除之前用户已经阅读过的记录
        userNoticeCheckDao.delete(
                Wraps.lbQ(new UserNoticeCheckEntity())
                        .eq(UserNoticeCheckEntity::getNoticeId, id)
        );
    }

    /**
     * 查询用户自己发布的公告信息
     *
     * @param noticeQueryDTO
     * @param userId
     * @return
     */
    @Override
    public NoticePageVo noticeList(NoticeQueryDTO noticeQueryDTO, Long userId) {
        Integer pageNo = noticeQueryDTO.getPageNo();
        Integer pageSize = noticeQueryDTO.getPageSize();

        // 先查询总计数
        LbqWrapper<NoticeEntity> wrapper = Wraps.lbQ(new NoticeEntity())
                .eq(NoticeEntity::getUserId, userId);
        if (StrUtil.isNotEmpty(noticeQueryDTO.getTitle())) {
            wrapper.eq(NoticeEntity::getTitle, noticeQueryDTO.getTitle());
        }
        Integer count = baseMapper.selectCount(wrapper);
        if (count == 0) {
            return NoticePageVo.builder()
                    .total(count)
                    .noticeList(Collections.emptyList())
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .build();
        }

        noticeQueryDTO.setPageNo((pageNo - 1) * pageSize);
        List<NoticeEntity> noticeList = baseMapper.selectNoticeList(noticeQueryDTO, userId);

        return NoticePageVo.builder()
                .total(count)
                .noticeList(noticeList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 查询用户可以查看的公告信息
     * 班级角色 -> 班级下的学生查看
     * 学院角色 -> 学院下的班级和学生都能查看
     * 社团角色 -> 所有学院和班级、学生都可以查看
     * 社团联角色 -> 社团用户可以查看
     * 学生处 -> 所有用户都可以查看
     * 系统管理员 -> 所有用户都可以查看
     *
     * @param noticeQueryDTO
     * @param userId
     * @return
     */
    @Override
    public NoticeUserPageVo noticeListByUserId(NoticeQueryDTO noticeQueryDTO, Long userId) {
        Integer pageNo = noticeQueryDTO.getPageNo();
        Integer pageSize = noticeQueryDTO.getPageSize();

        // 查询用户角色编码
        UserRoleVo userRoleVo = userRoleApi.findRoleIdByUserId(userId).getData();
        List<Long> userIds = new ArrayList<>();
        if (RoleEnum.STUDENT.name().equals(userRoleVo.getCode())) {
            // 学生  除社团联的公告，其他的都能看 并且学生只能查看本学院和本班级下的公告

            // 查询学生详细信息
            StuInfoEntity stuInfo = stuInfoService.getOne(Wraps.lbQ(new StuInfoEntity())
                    .eq(StuInfoEntity::getUserId, userId));
            // 查询自己班级
            ClazzInfoEntity clazzInfo = clazzInfoService.getOne(
                    Wraps.lbQ(new ClazzInfoEntity())
                            .eq(ClazzInfoEntity::getDepartment, stuInfo.getDepartment())
                            .eq(ClazzInfoEntity::getClazz, stuInfo.getClazz())
                            .eq(ClazzInfoEntity::getGrade, stuInfo.getGrade())
                            .eq(ClazzInfoEntity::getMajor, stuInfo.getMajor())
            );
            // 查询学院信息
            List<Long> departmentIds = getDepartmentIds(stuInfo.getDepartment());

            // 查询 社团、学生处、系统管理员Id
            List<Long> roleIdList = getRoleIdList();

            userIds.add(clazzInfo.getUserId());
            userIds.addAll(departmentIds);
            userIds.addAll(roleIdList);
        } else if (RoleEnum.CLASS_GRADE.name().equals(userRoleVo.getCode())) {
            // 班级用户  查看学院、社团、学生处、系统管理员的公告信息
            ClazzInfoEntity clazzInfo = clazzInfoService.getOne(
                    Wraps.lbQ(new ClazzInfoEntity())
                            .eq(ClazzInfoEntity::getUserId, userId)
            );

            // 查询学院信息
            List<Long> departmentIds = getDepartmentIds(clazzInfo.getDepartment());

            // 查询 社团、学生处、系统管理员Id
            List<Long> roleIdList = getRoleIdList();

            userIds.addAll(departmentIds);
            userIds.addAll(roleIdList);
        } else if (RoleEnum.YUAN_LEVEL_LEADER.name().equals(userRoleVo.getCode())) {
            // 学院  查看社团、学生处、系统管理员的公告信息
            userIds = roleApi.findUserIdByCode(
                    (String[]) Arrays.asList(
                            RoleEnum.SOCIAL_ORGANIZATION.name(),
                            RoleEnum.STU_OFFICE_ADMIN.name(),
                            RoleEnum.PT_ADMIN.name()
                    ).toArray()
            ).getData();
        } else if (RoleEnum.SOCIAL_ORGANIZATION.name().equals(userRoleVo.getCode())) {
            // 社团  查看社团联、学生处、系统管理员
            userIds = roleApi.findUserIdByCode(
                    (String[]) Arrays.asList(
                            RoleEnum.SHETUANLIAN_LEADER.name(),
                            RoleEnum.STU_OFFICE_ADMIN.name(),
                            RoleEnum.PT_ADMIN.name()
                    ).toArray()
            ).getData();

        } else {
            // 社团联和学生处和系统管理员  查看学生处、系统管理员
            userIds = roleApi.findUserIdByCode(
                    (String[]) Arrays.asList(
                            RoleEnum.STU_OFFICE_ADMIN.name(),
                            RoleEnum.PT_ADMIN.name()
                    ).toArray()
            ).getData();
        }

        // 先查询总记录数
        Integer count = baseMapper.selectCount(
                Wraps.lbQ(new NoticeEntity())
                        .in(NoticeEntity::getUserId, userIds)
        );
        if (count == 0) {
            return NoticeUserPageVo.builder()
                    .pageNo(pageNo)
                    .noticeList(Collections.emptyList())
                    .pageSize(pageSize)
                    .total(count)
                    .build();
        }

        noticeQueryDTO.setPageNo((pageNo - 1) * pageSize);
        List<NoticeEntity> noticeList = baseMapper.selectNoticeListByUserIds(noticeQueryDTO, userIds);

        // 查询用户公告记录
        List<UserNoticeCheckEntity> noticeCheckList = userNoticeCheckDao.selectList(
                Wraps.lbQ(new UserNoticeCheckEntity())
                        .in(UserNoticeCheckEntity::getNoticeId, noticeList.stream()
                                .map(SuperEntity::getId)
                                .collect(Collectors.toList())
                        )
                        .eq(UserNoticeCheckEntity::getUserId, userId)
        );

        Map<Long, UserNoticeCheckEntity> noticeCheckMap = Collections.emptyMap();
        if (ObjectUtil.isNotEmpty(noticeCheckList)) {
            noticeCheckMap = noticeCheckList.stream()
                    .collect(Collectors.toMap(
                                    UserNoticeCheckEntity::getNoticeId,
                                    Function.identity()
                            )
                    );
        }

        Map<Long, UserNoticeCheckEntity> finalNoticeCheckMap = noticeCheckMap;
        List<NoticeRo> noticeRoList = noticeList.stream()
                .map(n -> NoticeRo.builder()
                        .id(n.getId())
                        .userId(n.getUserId())
                        .title(n.getTitle())
                        .publicName(n.getPublicName())
                        .tag(RoleEnum.get(n.getUserCode()).getRoleType())
                        .readNum(n.getReadNum())
                        .updateTime(n.getUpdateTime())
                        .isCheck(
                                ObjectUtil.isEmpty(finalNoticeCheckMap.getOrDefault(n.getId(), null))
                                        ? Boolean.FALSE : Boolean.TRUE)
                        .build()
                )
                .collect(Collectors.toList());

        return NoticeUserPageVo.builder()
                .pageNo(pageNo)
                .noticeList(noticeRoList)
                .pageSize(pageSize)
                .total(count)
                .build();
    }

    /**
     * 查询学院信息
     * @param department
     * @return
     */
    private List<Long> getDepartmentIds(DictDepartmentEnum department) {
        return collegeInformationService.list(
                        Wraps.lbQ(new CollegeInformationEntity())
                                .eq(CollegeInformationEntity::getOrganizationCode, department.name())
                ).stream()
                .map(CollegeInformationEntity::getUserId)
                .collect(Collectors.toList());
    }

    /**
     * 查询 社团、学生处、系统管理员Id
     *
     * @return
     */
    private List<Long> getRoleIdList() {
        return roleApi.findUserIdByCode(
                (String[]) Arrays.asList(
                        RoleEnum.SOCIAL_ORGANIZATION.name(),
                        RoleEnum.STU_OFFICE_ADMIN.name(),
                        RoleEnum.PT_ADMIN.name()
                ).toArray()
        ).getData();
    }
}