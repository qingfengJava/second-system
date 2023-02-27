package com.qingfeng.cms.biz.sign.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.apply.dao.ApplyDao;
import com.qingfeng.cms.biz.sign.dao.ActiveSignDao;
import com.qingfeng.cms.biz.sign.service.ActiveSignService;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.apply.enums.IsReleaseEnum;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
import com.qingfeng.cms.domain.sign.dto.ActiveQueryDTO;
import com.qingfeng.cms.domain.sign.dto.ActiveSignSaveDTO;
import com.qingfeng.cms.domain.sign.entity.ActiveSignEntity;
import com.qingfeng.cms.domain.sign.enums.EvaluationStatusEnum;
import com.qingfeng.cms.domain.sign.enums.SignStatusEnum;
import com.qingfeng.cms.domain.sign.vo.ApplyPageVo;
import com.qingfeng.cms.domain.sign.vo.ApplyVo;
import com.qingfeng.cms.domain.sign.vo.OrganizeVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.sdk.auth.role.RoleApi;
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
    private DozerUtils dozerUtils;

    @Autowired
    private RoleApi roleApi;
    @Autowired
    private OrganizeInfoApi organizeInfoApi;

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
                                Function.identity()));
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

        applyVoList.forEach(a -> {


                    a.setActiveSignEntity(
                            activeSignEntityConcurrentMap.getOrDefault(a.getId(),
                                    null)
                    );
                    a.setSignNum(applyIdMap.getOrDefault(a.getId(), 0L));
                }
        );


        return ApplyPageVo.builder()
                .total(count)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .applyVoList(applyVoList)
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
}