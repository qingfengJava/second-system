package com.qingfeng.cms.biz.feedback.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.dict.service.DictService;
import com.qingfeng.cms.biz.feedback.dao.SystemFeedbackDao;
import com.qingfeng.cms.biz.feedback.service.SystemFeedbackService;
import com.qingfeng.cms.biz.student.service.StuInfoService;
import com.qingfeng.cms.domain.dict.entity.DictEntity;
import com.qingfeng.cms.domain.feedback.dto.SystemFeedbackSaveDTO;
import com.qingfeng.cms.domain.feedback.entity.SystemFeedbackEntity;
import com.qingfeng.cms.domain.feedback.vo.UserLeaderVo;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.authority.entity.auth.vo.UserInfoVo;
import com.qingfeng.currency.authority.entity.auth.vo.UserRoleVo;
import com.qingfeng.currency.authority.entity.core.Org;
import com.qingfeng.currency.authority.entity.core.Station;
import com.qingfeng.currency.common.enums.RoleEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.sdk.auth.org.OrgApi;
import com.qingfeng.sdk.auth.role.RoleApi;
import com.qingfeng.sdk.auth.role.UserRoleApi;
import com.qingfeng.sdk.auth.station.StationApi;
import com.qingfeng.sdk.auth.user.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 系统反馈表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:25
 */
@Service
public class SystemFeedbackServiceImpl extends ServiceImpl<SystemFeedbackDao, SystemFeedbackEntity> implements SystemFeedbackService {

    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private StuInfoService stuInfoService;
    @Autowired
    private DictService dictService;

    @Autowired
    private RoleApi roleApi;
    @Autowired
    private UserRoleApi userRoleApi;
    @Autowired
    private UserApi userApi;
    @Autowired
    private OrgApi orgApi;
    @Autowired
    private StationApi stationApi;

    /**
     * 保存系统反馈的信息
     *
     * @param systemFeedbackSaveDTO
     * @param userId
     */
    @Override
    public void saveSystemFeedback(SystemFeedbackSaveDTO systemFeedbackSaveDTO, Long userId) {
        //对于反馈信息可以直接进行存储
        SystemFeedbackEntity systemFeedbackEntity = dozerUtils.map2(systemFeedbackSaveDTO, SystemFeedbackEntity.class);
        systemFeedbackEntity.setUserId(userId);
        User user = userApi.get(userId).getData();
        systemFeedbackEntity.setUserName(user.getName());
        //查 反馈对象对应的Id和名字   如果是学院，要查询用户对应的学院信息
        List<UserInfoVo> userInfoVoList = roleApi.findUserInfoByCode(systemFeedbackSaveDTO.getFeedbackType()).getData();
        if (systemFeedbackSaveDTO.getFeedbackType().equalsIgnoreCase(RoleEnum.STUDENT.name())){
            //学生
            StuInfoEntity stuInfoEntity = stuInfoService.getOne(Wraps.lbQ(new StuInfoEntity())
                    .eq(StuInfoEntity::getUserId, userId));
            DictEntity dict = dictService.getOne(Wraps.lbQ(new DictEntity())
                    .eq(DictEntity::getDictCode, stuInfoEntity.getDepartment()));

            //根据dictName 查询组织和岗位Id，然后定位查询用户
            Org org = orgApi.getOrgByDictName(dict.getDictName()).getData();
            Station station = stationApi.findByOrgId(org.getId()).getData()
                    .stream()
                    .filter(s -> s.getName().contains("负责人"))
                    .findAny().get();
            User data = userApi.getByOrgIdAndStationId(org.getId(), station.getId()).getData();
            systemFeedbackEntity.setFeedbackObjectId(String.valueOf(data.getId()));
            systemFeedbackEntity.setFeedbackObjectName(data.getName());

        }else{
            systemFeedbackEntity.setFeedbackObjectId(userInfoVoList.stream()
                    .map(userInfoVo -> String.valueOf(userInfoVo.getUserId()))
                    .distinct()
                    .collect(Collectors.joining("、")));
            systemFeedbackEntity.setFeedbackObjectId(userInfoVoList.stream()
                    .map(UserInfoVo::getName)
                    .distinct()
                    .collect(Collectors.joining("、")));
        }

        baseMapper.insert(systemFeedbackEntity);
    }

    /**
     * 查询当前用户领导信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserLeaderVo> getLeader(Long userId) {
        //先判断当前用户的类型
        UserRoleVo userRoleVo = userRoleApi.findRoleIdByUserId(userId).getData();
        if (ObjectUtil.isNotEmpty(userRoleVo)){
            if (RoleEnum.STUDENT.name().equals(userRoleVo.getCode())) {
                //学生  社团联、（自己）学院、学生处处长、系统管理员
                return Arrays.stream(RoleEnum.values()).filter(r ->
                                r.name().equals(RoleEnum.SHETUANLIAN_LEADER.name())
                                        || r.name().equals(RoleEnum.YUAN_LEVEL_LEADER.name())
                                        || r.name().equals(RoleEnum.STU_OFFICE_ADMIN.name())
                                        || r.name().equals(RoleEnum.PT_ADMIN.name()))
                        .map(r -> UserLeaderVo.builder()
                                .userCode(r.name())
                                .userName(r.getRoleType())
                                .build())
                        .collect(Collectors.toList());

            } else if (RoleEnum.SOCIAL_ORGANIZATION.name().equals(userRoleVo.getCode())) {
                //社团组织  社团联、学生处处长、系统管理员
                return Arrays.stream(RoleEnum.values()).filter(r ->
                                r.name().equals(RoleEnum.SHETUANLIAN_LEADER.name())
                                        || r.name().equals(RoleEnum.STU_OFFICE_ADMIN.name())
                                        || r.name().equals(RoleEnum.PT_ADMIN.name()))
                        .map(r -> UserLeaderVo.builder()
                                .userCode(r.name())
                                .userName(r.getRoleType())
                                .build())
                        .collect(Collectors.toList());

            } else if (RoleEnum.SHETUANLIAN_LEADER.name().equals(userRoleVo.getCode())
                    || RoleEnum.YUAN_LEVEL_LEADER.name().equals(userRoleVo.getCode())) {
                //社团联组织、二级学院组织    学生处处长、系统管理员
                return Arrays.stream(RoleEnum.values()).filter(r ->
                                r.name().equals(RoleEnum.STU_OFFICE_ADMIN.name())
                                        || r.name().equals(RoleEnum.PT_ADMIN.name()))
                        .map(r -> UserLeaderVo.builder()
                                .userCode(r.name())
                                .userName(r.getRoleType())
                                .build())
                        .collect(Collectors.toList());

            } else if (RoleEnum.STU_OFFICE_ADMIN.name().equals(userRoleVo.getCode())) {
                //学生处   系统管理员
                return Arrays.stream(RoleEnum.values()).filter(r -> r.name().equals(RoleEnum.PT_ADMIN.name()))
                        .map(r -> UserLeaderVo.builder()
                                .userCode(r.name())
                                .userName(r.getRoleType())
                                .build())
                        .collect(Collectors.toList());
            }
        }

        return null;
    }
}