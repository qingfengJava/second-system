package com.qingfeng.cms.biz.total.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.club.service.ClubScoreModuleService;
import com.qingfeng.cms.biz.item.service.ItemAchievementModuleService;
import com.qingfeng.cms.biz.total.dao.StudentScoreTotalDao;
import com.qingfeng.cms.biz.total.service.StudentScoreTotalService;
import com.qingfeng.cms.domain.club.entity.ClubScoreModuleEntity;
import com.qingfeng.cms.domain.item.entity.ItemAchievementModuleEntity;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.module.enums.CreditModuleTypeEnum;
import com.qingfeng.cms.domain.total.entity.StudentScoreTotalEntity;
import com.qingfeng.cms.domain.total.ro.ModuleRo;
import com.qingfeng.cms.domain.total.vo.StudentScoreDetailsVo;
import com.qingfeng.cms.domain.total.vo.StudentScoreTotalVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.sdk.planstandard.module.CreditModuleApi;
import com.qingfeng.sdk.planstandard.plan.PlanApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 用户总得分情况
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-15 11:50:15
 */
@Service
public class StudentScoreTotalServiceImpl extends ServiceImpl<StudentScoreTotalDao, StudentScoreTotalEntity> implements StudentScoreTotalService {

    @Autowired
    private ItemAchievementModuleService itemAchievementModuleService;
    @Autowired
    private ClubScoreModuleService clubScoreModuleService;

    @Autowired
    private CreditModuleApi creditModuleApi;
    @Autowired
    private PlanApi planApi;

    /**
     * 查询学生模块得分情况
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public StudentScoreTotalVo stuModuleScore(Long userId) {
        // 模块信息
        List<CreditModuleEntity> moduleList = creditModuleApi.moduleListByStuId().getData();
        // 查询学生总得分信息
        StudentScoreTotalEntity studentScoreTotal = baseMapper.selectOne(
                Wraps.lbQ(new StudentScoreTotalEntity())
                        .eq(StudentScoreTotalEntity::getUserId, userId)
        );

        if (ObjectUtil.isEmpty(studentScoreTotal)) {
            Integer totalScore = planApi.getPlanByUserId(userId).getData().getTotalScore();
            // 说明还没有参加任务活动
            List<ModuleRo> moduleRoList = moduleList.stream()
                    .map(m -> ModuleRo.builder()
                            .moduleName(m.getModuleName())
                            .creditsEarned(BigDecimal.valueOf(0))
                            .creditsDue(BigDecimal.valueOf(m.getMinScore()))
                            .build()
                    )
                    .collect(Collectors.toList());

            return StudentScoreTotalVo.builder()
                    .userId(userId)
                    .score(BigDecimal.valueOf(0))
                    .creditsScore(BigDecimal.valueOf(totalScore))
                    .moduleList(moduleRoList)
                    .build();
        }

        // 查询学生每个模块所修的学分
        List<ItemAchievementModuleEntity> itemAchievementModuleList = itemAchievementModuleService.list(
                Wraps.lbQ(new ItemAchievementModuleEntity())
                        .eq(ItemAchievementModuleEntity::getUserId, userId)
        );
        Map<Long, Double> itemModuleMap = Collections.emptyMap();
        if (CollUtil.isNotEmpty(itemAchievementModuleList)) {
            itemModuleMap = itemAchievementModuleList.stream()
                    .collect(Collectors.groupingBy(
                            ItemAchievementModuleEntity::getModuleId,
                            Collectors.summingDouble(i -> i.getScore().doubleValue())
                    ));
        }

        // 查询社团活动得分
        List<ClubScoreModuleEntity> clubScoreModuleList = clubScoreModuleService.list(
                Wraps.lbQ(new ClubScoreModuleEntity())
                        .eq(ClubScoreModuleEntity::getUserId, userId)
        );
        Double clubModuleCount = 0.00;
        if (CollUtil.isNotEmpty(clubScoreModuleList)) {
            clubModuleCount = clubScoreModuleList.stream()
                    .collect(Collectors.summingDouble(c -> c.getScore().doubleValue()));
        }

        // 封装模块活动
        Map<Long, Double> finalItemModuleMap = itemModuleMap;
        Double finalClubModuleCount = clubModuleCount;
        List<ModuleRo> moduleRoList = moduleList.stream()
                .map(m -> ModuleRo.builder()
                        .moduleName(m.getModuleName())
                        .creditsEarned(
                                m.getCode().eq(CreditModuleTypeEnum.COMMUNITY_WORK) ?
                                        BigDecimal.valueOf(
                                                        finalItemModuleMap.getOrDefault(m.getId(), 0.00)
                                                )
                                                .add(BigDecimal.valueOf(finalClubModuleCount))
                                        : BigDecimal.valueOf(
                                        finalItemModuleMap.getOrDefault(m.getId(), 0.00)
                                )
                        )
                        .creditsDue(BigDecimal.valueOf(m.getMinScore()))
                        .build()
                )
                .collect(Collectors.toList());

        return StudentScoreTotalVo.builder()
                .userId(userId)
                .score(studentScoreTotal.getScore())
                .creditsScore(studentScoreTotal.getCreditsScore())
                .moduleList(moduleRoList)
                .build();
    }

    /**
     * 查询学生得分详情
     *
     * @param userId
     * @return
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public List<StudentScoreDetailsVo> queryScoreDetails(Long userId) {
        // 先查询学生是否有加分信息
        StudentScoreTotalEntity studentScoreTotal = baseMapper.selectOne(
                Wraps.lbQ(new StudentScoreTotalEntity())
                        .eq(StudentScoreTotalEntity::getUserId, userId)
        );
        if (ObjectUtil.isEmpty(studentScoreTotal)) {
            return Collections.emptyList();
        }

        // 查询模块加分详情
        List<ItemAchievementModuleEntity> itemAchievementModuleList = itemAchievementModuleService.list(
                Wraps.lbQ(new ItemAchievementModuleEntity())
                        .eq(ItemAchievementModuleEntity::getUserId, userId)
        );
        if (CollUtil.isNotEmpty(itemAchievementModuleList)) {
            // 封装模块的信息


        }

        // 社团活动信息
        List<ClubScoreModuleEntity> clubScoreModuleList = clubScoreModuleService.list(
                Wraps.lbQ(new ClubScoreModuleEntity())
                        .eq(ClubScoreModuleEntity::getUserId, userId)
        );
        if (CollUtil.isNotEmpty(clubScoreModuleList)) {
            // 封装社团活动信息

        }

        return null;
    }
}