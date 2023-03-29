package com.qingfeng.cms.biz.statistics.service;

import cn.hutool.core.collection.CollUtil;
import com.qingfeng.cms.biz.club.service.ClubScoreModuleService;
import com.qingfeng.cms.biz.item.service.ItemAchievementModuleService;
import com.qingfeng.cms.biz.total.service.StudentScoreTotalService;
import com.qingfeng.cms.domain.clazz.vo.UserVo;
import com.qingfeng.cms.domain.club.entity.ClubScoreModuleEntity;
import com.qingfeng.cms.domain.item.entity.ItemAchievementModuleEntity;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.module.enums.CreditModuleTypeEnum;
import com.qingfeng.cms.domain.statistics.ro.StuSemesterCreditsRo;
import com.qingfeng.cms.domain.statistics.vo.ClassModuleVo;
import com.qingfeng.cms.domain.statistics.vo.ClazzCreditsVo;
import com.qingfeng.cms.domain.statistics.vo.StuSemesterCreditsVo;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.cms.domain.total.entity.StudentScoreTotalEntity;
import com.qingfeng.cms.domain.total.vo.StuModuleDataAnalysisVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.sdk.messagecontrol.StuInfo.StuInfoApi;
import com.qingfeng.sdk.messagecontrol.clazz.ClazzInfoApi;
import com.qingfeng.sdk.planstandard.module.CreditModuleApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/28
 */
@Service
public class StatisticsService {

    @Autowired
    private ClubScoreModuleService clubScoreModuleService;
    @Autowired
    private ItemAchievementModuleService itemAchievementModuleService;
    @Autowired
    private StudentScoreTotalService studentScoreTotalService;

    @Autowired
    private StuInfoApi stuInfoApi;
    @Autowired
    private CreditModuleApi creditModuleApi;
    @Autowired
    private ClazzInfoApi clazzInfoApi;


    /**
     * 学生学期学分修读情况
     *
     * @param userId
     * @return
     */
    public StuSemesterCreditsVo stuSemesterCredits(Long userId) {
        // 查询学生基本信息
        StuInfoEntity stuInfo = stuInfoApi.info(userId).getData();
        // 根据年级封装学期信息
        List<String> schoolYearList = this.getSchoolYear(stuInfo);
        // 查询各个模块下的得分情况 然后按照学期进行分组

        // 查询社团活动信息
        List<ClubScoreModuleEntity> clubScoreModuleList = clubScoreModuleService.list(
                Wraps.lbQ(new ClubScoreModuleEntity())
                        .eq(ClubScoreModuleEntity::getUserId, userId)
        );
        // 社团活动根据学期进行的分组
        Map<String, List<ClubScoreModuleEntity>> schoolYearClubScoreMap = new HashMap<>(16);
        if (CollUtil.isNotEmpty(clubScoreModuleList)) {
            schoolYearClubScoreMap = clubScoreModuleList.stream()
                    .collect(Collectors.groupingBy(
                                    ClubScoreModuleEntity::getSchoolYear
                            )
                    );
        }

        // 查询学生每个模块所修的学分
        List<ItemAchievementModuleEntity> itemAchievementModuleList = itemAchievementModuleService.list(
                Wraps.lbQ(new ItemAchievementModuleEntity())
                        .eq(ItemAchievementModuleEntity::getUserId, userId)
        );
        // 项目根据学期分组
        Map<String, List<ItemAchievementModuleEntity>> schoolYearItemMap = new HashMap<>();
        if (CollUtil.isNotEmpty(itemAchievementModuleList)) {
            schoolYearItemMap = itemAchievementModuleList.stream()
                    .collect(Collectors.groupingBy(
                            ItemAchievementModuleEntity::getSchoolYear
                    ));
        }

        // 封装折线的标题
        List<String> legendData = new ArrayList<>(10);
        Map<Long, String> legendDataMap = new HashMap<>(16);
        legendDataMap.put(0L, "所修学分");
        legendData.add("所修学分");
        List<CreditModuleEntity> moduleEntityList = creditModuleApi.moduleListByStuId()
                .getData();
        moduleEntityList
                .forEach(c -> {
                    legendData.add(c.getModuleName());
                    legendDataMap.put(c.getId(), c.getModuleName());
                });


        // 根据折线标题封装数
        Map<String, List<ClubScoreModuleEntity>> finalSchoolYearClubScoreMap = schoolYearClubScoreMap;
        Map<String, List<ItemAchievementModuleEntity>> finalSchoolYearItemMap = schoolYearItemMap;
        List<StuSemesterCreditsRo> stuSemesterCreditsRos = legendDataMap.entrySet().stream()
                .map(entry -> {
                    // 根据学期进行遍历
                    List<Double> doubleData = schoolYearList.stream()
                            .map(schoolYear -> {
                                // 拿每个学期下的活动信息
                                List<ClubScoreModuleEntity> clubScoreModuleEntityList = finalSchoolYearClubScoreMap.getOrDefault(
                                        schoolYear,
                                        Collections.emptyList()
                                );
                                // 拿每个学期下的项目信息
                                List<ItemAchievementModuleEntity> itemAchievementModuleEntityList = finalSchoolYearItemMap.getOrDefault(
                                        schoolYear,
                                        Collections.emptyList()
                                );

                                Double cluValue = clubScoreModuleEntityList.size() > 0
                                        ? clubScoreModuleEntityList.stream()
                                        .collect(
                                                Collectors.summingDouble(
                                                        c -> c.getScore().doubleValue()
                                                )
                                        ) : 0.00;
                                if (entry.getKey() == 0) {
                                    // 所修学分
                                    Double itemValue = itemAchievementModuleEntityList.size() > 0
                                            ? itemAchievementModuleEntityList.stream()
                                            .collect(Collectors.summingDouble(
                                                            c -> c.getScore().doubleValue()
                                                    )
                                            ) : 0.00;
                                    return cluValue + itemValue;
                                } else {
                                    // 根据模块查询每个学期下的学分
                                    Double itemValue = itemAchievementModuleEntityList.stream()
                                            .filter(i -> i.getModuleId().equals(entry.getKey()))
                                            .collect(Collectors.toList())
                                            .stream()
                                            .collect(Collectors.summingDouble(
                                                            c -> c.getScore().doubleValue()
                                                    )
                                            );

                                    if (entry.getValue().contains("社团")) {
                                        return itemValue + cluValue;
                                    }

                                    return itemValue;
                                }
                            })
                            .collect(Collectors.toList());


                    return StuSemesterCreditsRo.builder()
                            .name(entry.getValue())
                            .data(doubleData)
                            .build();
                })
                .collect(Collectors.toList());


        return StuSemesterCreditsVo.builder()
                .schoolTerm(schoolYearList)
                .stuSemesterCreditsRoList(stuSemesterCreditsRos)
                .legendData(legendData)
                .build();
    }

    private List<String> getSchoolYear(StuInfoEntity stuInfo) {
        int year = Integer.parseInt(stuInfo.getGrade().substring(0, 4));
        List<String> schoolYearList = new ArrayList<>(10);
        for (int i = 1; i <= stuInfo.getEducationalSystem().getVariable(); i++) {
            schoolYearList.add(year + "-" + (year + 1) + "  第一学期");
            schoolYearList.add(year + "-" + (year + 1) + "  第二学期");
            year++;
        }

        return schoolYearList;
    }

    /**
     * 班级的方案模块下的学生参与人数
     *
     * @param userId
     * @return
     */
    public ClassModuleVo classModule(Long userId) {
        // 查询班级下的学生
        List<UserVo> stuList = clazzInfoApi.stuList().getData();
        // 查询方案模块信息
        List<CreditModuleEntity> moduleList = creditModuleApi.clazzModule().getData();
        if (CollUtil.isEmpty(stuList)) {
            // 还没有学生，直接返回空数据
            List<String> xData = new ArrayList<>();
            List<Integer> data = new ArrayList<>();
            moduleList.forEach(m -> {
                xData.add(m.getModuleName());
                data.add(0);
            });

            return ClassModuleVo.builder()
                    .xData(xData)
                    .data(data)
                    .build();
        }

        List<Long> stuIds = stuList.stream()
                .map(UserVo::getId)
                .collect(Collectors.toList());

        // 查询社团活动情况
        List<ClubScoreModuleEntity> clubScoreModuleList = clubScoreModuleService.list(
                Wraps.lbQ(new ClubScoreModuleEntity())
                        .in(ClubScoreModuleEntity::getUserId, stuIds)
        );
        // 查询项目情况
        List<ItemAchievementModuleEntity> itemAchievementModuleList = itemAchievementModuleService.list(
                Wraps.lbQ(new ItemAchievementModuleEntity())
                        .in(ItemAchievementModuleEntity::getUserId, stuIds)
        );

        if (CollUtil.isEmpty(clubScoreModuleList) && CollUtil.isEmpty(itemAchievementModuleList)) {
            // 还没有学生，直接返回空数据
            List<String> xData = new ArrayList<>();
            List<Integer> data = new ArrayList<>();
            moduleList.forEach(m -> {
                xData.add(m.getModuleName());
                data.add(0);
            });

            return ClassModuleVo.builder()
                    .xData(xData)
                    .data(data)
                    .build();
        }

        // 方案模块根据模块Id进行分组
        Map<Long, Long> itemMap = new HashMap<>();
        if (CollUtil.isNotEmpty(itemAchievementModuleList)) {
            itemMap = itemAchievementModuleList.stream()
                    .collect(Collectors.groupingBy(
                                    ItemAchievementModuleEntity::getModuleId,
                                    Collectors.counting()
                            )
                    );
        }

        List<String> xData = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        Map<Long, Long> finalItemMap = itemMap;
        moduleList.forEach(m -> {
            xData.add(m.getModuleName());
            if (m.getCode().equals(CreditModuleTypeEnum.COMMUNITY_WORK)) {
                data.add(finalItemMap.getOrDefault(m.getId(), 0L).intValue() + clubScoreModuleList.size());
            } else {
                data.add(finalItemMap.getOrDefault(m.getId(), 0L).intValue());
            }
        });


        return ClassModuleVo.builder()
                .xData(xData)
                .data(data)
                .build();
    }

    /**
     * 班级的学生学分修读情况
     *
     * @return
     */
    public List<ClazzCreditsVo> clazzCredits() {
        // 查询班级下的学生信息
        List<UserVo> userList = clazzInfoApi.stuList().getData();
        if (CollUtil.isEmpty(userList)) {
            return Collections.singletonList(
                    ClazzCreditsVo.builder()
                            .name("无学生参与")
                            .y(0)
                            .build()
            );
        }

        // 查询学生成绩
        List<Long> userIds = userList.stream()
                .map(UserVo::getId)
                .collect(Collectors.toList());
        List<StudentScoreTotalEntity> scoreTotalList = studentScoreTotalService.list(
                Wraps.lbQ(new StudentScoreTotalEntity())
                        .in(StudentScoreTotalEntity::getUserId, userIds)
        );

        List<Long> scoreTotalListIds = scoreTotalList.stream()
                .map(StudentScoreTotalEntity::getUserId)
                .collect(Collectors.toList());

        int count = scoreTotalList.get(0).getCreditsScore().intValue();

        ArrayList<ClazzCreditsVo> clazzCreditsVos = new ArrayList<>();
        clazzCreditsVos.add(
                ClazzCreditsVo.builder()
                        .name("0分以下的学生人数")
                        .y(userIds.stream()
                                .filter(id -> !scoreTotalListIds.contains(id))
                                .collect(Collectors.counting())
                                .intValue()
                        )
                        .build()
        );
        for (int i = 0; i < count; i++) {
            int finalI = i;
            clazzCreditsVos.add(ClazzCreditsVo.builder()
                    .name(i + "~" + (i + 1) + "分数段的人数")
                    .y(scoreTotalList.stream()
                            .filter(s ->
                                    ((s.getScore().compareTo(BigDecimal.valueOf(finalI)) == 0)
                                            || (s.getScore().compareTo(BigDecimal.valueOf(finalI)) == 1)
                                    )
                                            && s.getScore().compareTo(BigDecimal.valueOf(finalI + 1)) == -1
                            )
                            .collect(Collectors.counting())
                            .intValue()
                    )
                    .build());
        }
        clazzCreditsVos.add(
                ClazzCreditsVo.builder()
                        .name(count + "以上的学生人数")
                        .y(scoreTotalList.stream()
                                .filter(s -> (s.getScore().compareTo(s.getCreditsScore()) == 0 || s.getScore().compareTo(s.getCreditsScore()) == 1))
                                .collect(Collectors.counting())
                                .intValue()
                        )
                        .build()
        );


        return clazzCreditsVos;
    }

    /**
     * 根据学生Id，查询学生第二课堂参与情况
     * @param stuId
     * @return
     */
    public StuModuleDataAnalysisVo clazzStuId(Long stuId) {
        return studentScoreTotalService.moduleDataAnalysis(stuId);
    }
}
