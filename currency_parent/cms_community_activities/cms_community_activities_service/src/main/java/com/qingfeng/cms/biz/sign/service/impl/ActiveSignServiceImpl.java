package com.qingfeng.cms.biz.sign.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.apply.dao.ApplyDao;
import com.qingfeng.cms.biz.sign.dao.ActiveSignDao;
import com.qingfeng.cms.biz.sign.service.ActiveSignService;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.apply.enums.IsReleaseEnum;
import com.qingfeng.cms.domain.organize.entity.OrganizeImgEntity;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
import com.qingfeng.cms.domain.sign.dto.ActiveApplySignQueryDTO;
import com.qingfeng.cms.domain.sign.dto.ActiveQueryDTO;
import com.qingfeng.cms.domain.sign.dto.ActiveSignSaveDTO;
import com.qingfeng.cms.domain.sign.entity.ActiveSignEntity;
import com.qingfeng.cms.domain.sign.enums.EvaluationStatusEnum;
import com.qingfeng.cms.domain.sign.enums.SignStatusEnum;
import com.qingfeng.cms.domain.sign.ro.ActiveApplySignRo;
import com.qingfeng.cms.domain.sign.vo.ActiveApplySignVo;
import com.qingfeng.cms.domain.sign.vo.ActiveSignSaveVo;
import com.qingfeng.cms.domain.sign.vo.ApplyPageVo;
import com.qingfeng.cms.domain.sign.vo.ApplyVo;
import com.qingfeng.cms.domain.sign.vo.OrganizeVo;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.sdk.auth.role.RoleApi;
import com.qingfeng.sdk.auth.user.UserApi;
import com.qingfeng.sdk.messagecontrol.StuInfo.StuInfoApi;
import com.qingfeng.sdk.messagecontrol.organize.OrganizeImgApi;
import com.qingfeng.sdk.messagecontrol.organize.OrganizeInfoApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 活动报名表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Service
public class ActiveSignServiceImpl extends ServiceImpl<ActiveSignDao, ActiveSignEntity> implements ActiveSignService {

    private static final String ROLE_CODE = "SOCIAL_ORGANIZATION";

    @Autowired
    private ApplyDao applyDao;
    @Autowired
    private ActiveSignDao activeSignDao;
    @Autowired
    private DozerUtils dozerUtils;

    @Autowired
    private RoleApi roleApi;
    @Autowired
    private OrganizeInfoApi organizeInfoApi;
    @Autowired
    private OrganizeImgApi organizeImgApi;
    @Autowired
    private StuInfoApi stuInfoApi;
    @Autowired
    private UserApi userApi;

    /**
     * 查询社团组织列表
     *
     * @return
     */
    @Override
    public List<OrganizeVo> organizeList() {
        //根据社团角色编码，查询用户ID
        List<Long> userIds = roleApi.findUserIdByCode(new String[]{ROLE_CODE}).getData();
        //根据用户id查询对应的社团组织信息
        List<OrganizeInfoEntity> organizeInfoEntityList = organizeInfoApi.infoList(userIds).getData();
        return organizeInfoEntityList.stream()
                .map(o -> OrganizeVo.builder()
                        .userId(o.getUserId())
                        .organizeName(o.getOrganizeName())
                        .build())
                .collect(Collectors.toList());
    }

    /**
     * 查询已发布的活动（并且当前用户的报名信息一并查询）
     *
     * @param activeQueryDTO
     * @param userId
     * @return
     */
    @Override
    public ApplyPageVo applyList(ActiveQueryDTO activeQueryDTO, Long userId) {
        Integer pageNo = activeQueryDTO.getPageNo();
        Integer pageSize = activeQueryDTO.getPageSize();
        LbqWrapper<ApplyEntity> wrapper = Wraps.lbQ(new ApplyEntity())
                .eq(ApplyEntity::getIsRelease, IsReleaseEnum.FINISH);
        if (ObjectUtil.isNotEmpty(activeQueryDTO.getUserId())) {
            wrapper.eq(ApplyEntity::getApplyUserId, activeQueryDTO.getUserId());
        }
        Integer count = applyDao.selectCount(wrapper);
        if (count == 0) {
            return ApplyPageVo.builder()
                    .total(0)
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .applyVoList(Collections.emptyList())
                    .build();
        }
        activeQueryDTO.setPageNo((pageNo - 1) * pageSize);
        // 根据条件分页查询已发布的活动
        List<ApplyVo> applyVoList = applyDao.getApplyList(activeQueryDTO);
        //查询当前用户已经报名的活动
        ConcurrentMap<Long, ActiveSignEntity> activeSignEntityConcurrentMap =
                baseMapper.selectList(Wraps.lbQ(new ActiveSignEntity())
                                .eq(ActiveSignEntity::getUserId, userId))
                        .stream()
                        .collect(Collectors.toConcurrentMap(ActiveSignEntity::getApplyId,
                                Function.identity())
                        );
        //查询活动当前的报名人数
        Map<Long, Long> applyIdMap = baseMapper.selectList(Wraps.lbQ(new ActiveSignEntity())
                        .in(ActiveSignEntity::getApplyId, applyVoList.stream()
                                .map(ApplyVo::getId)
                                .collect(Collectors.toList()))
                )
                .stream()
                .collect(Collectors.groupingBy(
                        ActiveSignEntity::getApplyId,
                        Collectors.counting()
                ));

        // 查询对应的社团组织信息
        ConcurrentMap<Long, OrganizeInfoEntity> organizeInfoEntityConcurrentMap =
                organizeInfoApi.infoList(
                                applyVoList.stream()
                                        .map(ApplyVo::getApplyUserId)
                                        .distinct()
                                        .collect(Collectors.toList())
                        )
                        .getData()
                        .stream()
                        .collect(Collectors.toConcurrentMap(
                                        OrganizeInfoEntity::getUserId,
                                        Function.identity()
                                )
                        );

        // 查询社团对应的轮播图信息
        Map<Long, List<OrganizeImgEntity>> orgImgMap = organizeImgApi.listImg(
                organizeInfoEntityConcurrentMap
                        .entrySet()
                        .stream()
                        .map(e -> e.getValue().getId())
                        .collect(Collectors.toList())
        ).getData();


        applyVoList.forEach(a -> {
                    a.setActiveSignEntity(
                            activeSignEntityConcurrentMap.getOrDefault(a.getId(),
                                    null)
                    );
                    a.setSignNum(applyIdMap.getOrDefault(a.getId(), 0L));
                    a.setOrganizeInfoEntity(organizeInfoEntityConcurrentMap.get(a.getApplyUserId()));
                    a.setOrgImgs(orgImgMap.get(a.getOrganizeInfoEntity().getId()));
                }
        );

        // 查询用户信息
        StuInfoEntity stuInfoEntity = stuInfoApi.info(userId).getData();
        User user = userApi.get(userId).getData();

        return ApplyPageVo.builder()
                .total(count)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .applyVoList(applyVoList)
                .activeSignSaveVo(ActiveSignSaveVo.builder()
                        .userId(userId)
                        .studentNum(stuInfoEntity.getStudentNum())
                        .studentName(stuInfoEntity.getStudentName())
                        .studentCollege(ObjectUtil.isEmpty(stuInfoEntity.getDepartment())
                                ? null : stuInfoEntity.getDepartment().getDesc())
                        .studentMajor(stuInfoEntity.getMajor())
                        .studentSex(ObjectUtil.isEmpty(user.getSex())
                                ? null : user.getSex().getDesc())
                        .studentTel(user.getMobile())
                        .studentQq(stuInfoEntity.getQq())
                        .build())
                .build();
    }

    /**
     * 活动报名
     *
     * @param activeSignSaveDTO
     * @param userId
     */
    @Override
    public void saveSign(ActiveSignSaveDTO activeSignSaveDTO, Long userId) {
        ActiveSignEntity activeSignEntity = dozerUtils.map2(activeSignSaveDTO, ActiveSignEntity.class);
        if (ObjectUtil.isEmpty(activeSignEntity.getUserId())) {
            activeSignEntity.setUserId(userId);
        }
        activeSignEntity.setSignStatus(SignStatusEnum.INIT);
        activeSignEntity.setEvaluationStatus(EvaluationStatusEnum.INIT);
        baseMapper.insert(activeSignEntity);
    }

    /**
     * 分页查询用户报名的活动信息
     *
     * @param activeApplySignQueryDTO
     * @return
     */
    @Override
    public ActiveApplySignVo getActiveSignList(ActiveApplySignQueryDTO activeApplySignQueryDTO, Long userId) {
        // 先根据条件查询用户的报名信息
        Integer pageNo = activeApplySignQueryDTO.getPageNo();
        Integer pageSize = activeApplySignQueryDTO.getPageSize();
        activeApplySignQueryDTO.setPageNo((pageNo - 1) * pageSize);

        // 先查询总记录数
        Integer count = activeSignDao.selectSignCount(activeApplySignQueryDTO, userId);
        if (count == 0) {
            return ActiveApplySignVo.builder()
                    .total(count)
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .activeApplySignRoList(Collections.emptyList())
                    .build();
        }

        // 根据条件查询用户报名的活动信息
        List<ActiveSignEntity> activeSignEntityList = activeSignDao.selectSignList(activeApplySignQueryDTO, userId);
        ConcurrentMap<Long, ActiveSignEntity> activeSignEntityConcurrentMap = activeSignEntityList.stream()
                .collect(Collectors.toConcurrentMap(
                        ActiveSignEntity::getApplyId,
                        Function.identity())
                );
        // 查询活动信息
        List<ApplyEntity> applyEntityList = applyDao.selectList(Wraps.lbQ(new ApplyEntity())
                .in(ApplyEntity::getId, activeSignEntityList.stream()
                        .map(ActiveSignEntity::getApplyId)
                        .collect(Collectors.toList())
                )
        );
        ConcurrentMap<Long, OrganizeInfoEntity> organizeInfoEntityConcurrentMap = organizeInfoApi.infoList(
                        applyEntityList.stream()
                                .map(ApplyEntity::getApplyUserId)
                                .distinct()
                                .collect(Collectors.toList())
                )
                .getData()
                .stream()
                .collect(Collectors.toConcurrentMap(
                                OrganizeInfoEntity::getUserId,
                                Function.identity()
                        )
                );

        // 封装数据
        List<ActiveApplySignRo> activeApplySignRoList = applyEntityList.stream()
                .map(applyEntity -> ActiveApplySignRo.builder()
                        .apply(applyEntity)
                        .activeSign(activeSignEntityConcurrentMap.get(applyEntity.getId()))
                        .organizeInfo(organizeInfoEntityConcurrentMap.get(applyEntity.getApplyUserId()))
                        .build()
                )
                .collect(Collectors.toList());

        return ActiveApplySignVo.builder()
                .total(count)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .activeApplySignRoList(activeApplySignRoList)
                .build();
    }
}