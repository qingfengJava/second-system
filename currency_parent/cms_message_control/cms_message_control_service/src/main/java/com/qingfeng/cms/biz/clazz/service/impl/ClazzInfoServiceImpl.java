package com.qingfeng.cms.biz.clazz.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.clazz.dao.ClazzInfoDao;
import com.qingfeng.cms.biz.clazz.service.ClazzInfoService;
import com.qingfeng.cms.biz.student.service.StuInfoService;
import com.qingfeng.cms.domain.clazz.dto.ClazzInfoSaveDTO;
import com.qingfeng.cms.domain.clazz.entity.ClazzInfoEntity;
import com.qingfeng.cms.domain.clazz.vo.UserVo;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.sdk.auth.user.UserApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 班级信息
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-12 22:37:25
 */
@Service
public class ClazzInfoServiceImpl extends ServiceImpl<ClazzInfoDao, ClazzInfoEntity> implements ClazzInfoService {

    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private StuInfoService stuInfoService;
    @Autowired
    private UserApi userApi;

    /**
     * 保存班级信息
     *
     * @param clazzInfoSaveDTO
     */
    @Override
    public void saveClazzInfo(ClazzInfoSaveDTO clazzInfoSaveDTO, Long userId) {
        if (ObjectUtil.isNotEmpty(clazzInfoSaveDTO.getId())) {
            // 更新操作
            baseMapper.updateById(dozerUtils.map2(clazzInfoSaveDTO, ClazzInfoEntity.class));
        } else {
            // 保存操作
            ClazzInfoEntity clazzInfoEntity = baseMapper.selectOne(Wraps.lbQ(new ClazzInfoEntity())
                    .eq(ClazzInfoEntity::getUserId, userId));
            if (ObjectUtil.isEmpty(clazzInfoEntity)) {
                ClazzInfoEntity clazzInfo = dozerUtils.map2(clazzInfoSaveDTO, ClazzInfoEntity.class);
                clazzInfo.setUserId(userId);
                baseMapper.insert(clazzInfo);
            }
        }
    }

    /**
     * 查询班级下的学生信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<UserVo> stuList(Long userId) {
        // 先查询班级信息
        ClazzInfoEntity clazzInfo = baseMapper.selectOne(Wraps.lbQ(new ClazzInfoEntity())
                .eq(ClazzInfoEntity::getUserId, userId));
        if (ObjectUtil.isEmpty(clazzInfo)) {
            return Collections.emptyList();
        }

        // 查询stuInfo
        List<StuInfoEntity> stuInfoList = stuInfoService.list(Wraps.lbQ(new StuInfoEntity())
                .eq(StuInfoEntity::getDepartment, clazzInfo.getDepartment())
                .eq(StuInfoEntity::getMajor, clazzInfo.getMajor())
                .eq(StuInfoEntity::getGrade, clazzInfo.getGrade())
                .eq(StuInfoEntity::getEducationalSystem, clazzInfo.getEducationalSystem())
        );
        if (CollUtil.isEmpty(stuInfoList)) {
            return Collections.emptyList();
        }
        Map<Long, StuInfoEntity> stuInfoMap = stuInfoList.stream()
                .collect(Collectors.toMap(
                                StuInfoEntity::getUserId,
                                Function.identity()
                        )
                );

        // 查询用户基本信息
        List<User> userList = userApi.userInfoList(stuInfoList.stream()
                .map(StuInfoEntity::getUserId)
                .collect(Collectors.toList())
        ).getData();

        return userList.stream()
                .map(user -> UserVo.builder()
                        .id(user.getId())
                        .name(user.getName())
                        .email(user.getEmail())
                        .mobile(user.getMobile())
                        .sex(user.getSex().getDesc())
                        .avatar(user.getAvatar())
                        .stuInfoEntity(stuInfoMap.get(user.getId()))
                        .build()
                )
                .collect(Collectors.toList());
    }
}