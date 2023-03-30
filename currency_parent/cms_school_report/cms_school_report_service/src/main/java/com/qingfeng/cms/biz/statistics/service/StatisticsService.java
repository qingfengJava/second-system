package com.qingfeng.cms.biz.statistics.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.qingfeng.cms.biz.club.service.ClubScoreModuleService;
import com.qingfeng.cms.biz.item.service.ItemAchievementModuleService;
import com.qingfeng.cms.biz.total.service.StudentScoreTotalService;
import com.qingfeng.cms.domain.clazz.vo.UserVo;
import com.qingfeng.cms.domain.club.entity.ClubScoreModuleEntity;
import com.qingfeng.cms.domain.college.entity.CollegeInformationEntity;
import com.qingfeng.cms.domain.dict.enums.DictDepartmentEnum;
import com.qingfeng.cms.domain.item.entity.ItemAchievementModuleEntity;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.module.enums.CreditModuleTypeEnum;
import com.qingfeng.cms.domain.statistics.ro.GradeScoreRo;
import com.qingfeng.cms.domain.statistics.ro.StuSemesterCreditsRo;
import com.qingfeng.cms.domain.statistics.vo.ClassModuleVo;
import com.qingfeng.cms.domain.statistics.vo.ClazzCreditsVo;
import com.qingfeng.cms.domain.statistics.vo.GradeScoreVo;
import com.qingfeng.cms.domain.statistics.vo.StuSemesterCreditsVo;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.cms.domain.total.entity.StudentScoreTotalEntity;
import com.qingfeng.cms.domain.total.vo.OrganizeActiveVo;
import com.qingfeng.cms.domain.total.vo.StuModuleDataAnalysisVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.sdk.messagecontrol.StuInfo.StuInfoApi;
import com.qingfeng.sdk.messagecontrol.clazz.ClazzInfoApi;
import com.qingfeng.sdk.messagecontrol.collegeinformation.CollegeInformationApi;
import com.qingfeng.sdk.planstandard.module.CreditModuleApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
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
    @Autowired
    private CollegeInformationApi collegeInformationApi;


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
     *
     * @param stuId
     * @return
     */
    public StuModuleDataAnalysisVo clazzStuId(Long stuId) {
        return studentScoreTotalService.moduleDataAnalysis(stuId);
    }

    /**
     * 各年级学分修读情况
     *
     * @param userId
     * @return
     */
    public GradeScoreVo gradeScore(Long userId) {
        // 查询学院信息
        List<StuInfoEntity> stuInfoList = getStuInfoList(userId);

        // 如果没有学生就可以直接进行返回了
        if (CollUtil.isEmpty(stuInfoList)) {
            return GradeScoreVo.builder()
                    .xAxis(Collections.singletonList("无学生"))
                    .gradeScoreRoList(Collections.singletonList(
                                    GradeScoreRo.builder()
                                            .name("无学生")
                                            .data(Collections.singletonList(0))
                                            .build()
                            )
                    )
                    .build();
        }

        // 先将学生按照年级进行分组
        Map<String, List<StuInfoEntity>> stuGradeMap = stuInfoList.stream()
                .collect(Collectors.groupingBy(
                                StuInfoEntity::getGrade
                        )
                );

        // 取一个学生的学制 计算年级
        List<String> xAxis = getXAxis(stuInfoList.get(0).getEducationalSystem().getVariable());

        // 查询每个年级学生的学分修读情况
        List<Integer> dataYou = new ArrayList<>();
        List<Integer> dataLang = new ArrayList<>();
        List<Integer> dataCha = new ArrayList<>();
        xAxis.forEach(x -> {
                    // 进行保存 采用异步处理
                    // 查询每个年级学生的得分情况
                    List<StuInfoEntity> stuInfos = stuGradeMap.getOrDefault(x, null);
                    if (CollUtil.isEmpty(stuInfos)) {
                        dataYou.add(0);
                        dataLang.add(0);
                        dataCha.add(0);
                    } else {
                        // 查询每个学生的活动最终得分
                        List<StudentScoreTotalEntity> stuScoreList = studentScoreTotalService.list(
                                Wraps.lbQ(new StudentScoreTotalEntity())
                                        .in(
                                                StudentScoreTotalEntity::getUserId,
                                                stuInfos.stream()
                                                        .map(StuInfoEntity::getUserId)
                                                        .collect(Collectors.toList())
                                        )
                        );

                        // 学生还没有开始修学分
                        if (CollUtil.isEmpty(stuScoreList)) {
                            dataYou.add(0);
                            dataLang.add(0);
                            dataCha.add(stuInfos.size());
                        } else {
                            Map<Long, StudentScoreTotalEntity> stuScoreMap = stuScoreList.stream()
                                    .collect(Collectors.toMap(
                                                    StudentScoreTotalEntity::getUserId,
                                                    Function.identity()
                                            )
                                    );
                            // 统计不同分数段的学生人数
                            final int[] you = {0};
                            final int[] lang = {0};
                            final int[] cha = {0};
                            stuScoreList.forEach(s -> {
                                StudentScoreTotalEntity scoreTotal = stuScoreMap.getOrDefault(s.getUserId(), null);
                                if (Objects.isNull(scoreTotal)) {
                                    cha[0] = cha[0] + 1;
                                } else if (scoreTotal.getScore().intValue() < (scoreTotal.getCreditsScore().intValue() / 2)) {
                                    cha[0] = cha[0] + 1;
                                } else if (scoreTotal.getScore().intValue() >= scoreTotal.getCreditsScore().intValue()) {
                                    you[0] = you[0] + 1;
                                } else {
                                    lang[0] = lang[0] + 1;
                                }
                            });
                            dataYou.add(you[0]);
                            dataLang.add(lang[0]);
                            dataCha.add(cha[0] + (stuInfos.size() - stuScoreList.size()));
                        }
                    }
                }
        );


        return GradeScoreVo.builder()
                .xAxis(xAxis)
                .gradeScoreRoList(Arrays.asList(
                        GradeScoreRo.builder()
                                .name("差（未达到一半学分）")
                                .data(dataCha)
                                .build(),
                        GradeScoreRo.builder()
                                .name("良（完成一半学分）")
                                .data(dataLang)
                                .build(),
                        GradeScoreRo.builder()
                                .name("优（已完成学分）")
                                .data(dataYou)
                                .build()
                ))
                .build();
    }

    /**
     * 各年级各模块参与人数
     *
     * @param userId
     * @return
     */
    public GradeScoreVo gradeModule(Long userId, String grade) {
        List<StuInfoEntity> stuInfoList = getStuInfoList(userId);

        // 如果没有学生就可以直接进行返回了
        if (CollUtil.isEmpty(stuInfoList)) {
            return GradeScoreVo.builder()
                    .xAxis(Collections.singletonList("无学生"))
                    .gradeScoreRoList(Collections.singletonList(
                                    GradeScoreRo.builder()
                                            .name("无学生")
                                            .data(Collections.singletonList(0))
                                            .build()
                            )
                    )
                    .build();
        }

        // 过滤出需要查询的改年级的学生
        if (StrUtil.isEmpty(grade)) {
            grade = LocalDate.now().getMonthValue() > 9 ? LocalDate.now().getYear() + "级" : (LocalDate.now().getYear() - 1) + "级";
        }
        String finalGrade = grade;
        List<StuInfoEntity> stuGradeList = stuInfoList.stream()
                .filter(s -> s.getGrade().equals(finalGrade))
                .collect(Collectors.toList());

        if (CollUtil.isEmpty(stuGradeList)) {
            return GradeScoreVo.builder()
                    .xAxis(Collections.singletonList("无学生"))
                    .gradeScoreRoList(Collections.singletonList(
                                    GradeScoreRo.builder()
                                            .name("无学生")
                                            .data(Collections.singletonList(0))
                                            .build()
                            )
                    )
                    .build();
        }

        // 根据一个学生获取方案模块信息
        Map<Long, CreditModuleEntity> moduleMap = creditModuleApi.moduleListByStuId(stuGradeList.get(0).getUserId())
                .getData()
                .stream()
                .collect(Collectors.toMap(
                                CreditModuleEntity::getId,
                                Function.identity()
                        )
                );

        // 查询每个学生的社团活动
        List<ClubScoreModuleEntity> clubScoreModule = clubScoreModuleService.list(
                Wraps.lbQ(new ClubScoreModuleEntity())
                        .in(
                                ClubScoreModuleEntity::getUserId,
                                stuGradeList.stream()
                                        .map(StuInfoEntity::getUserId)
                                        .collect(Collectors.toList())
                        )
        );

        List<ItemAchievementModuleEntity> itemAchievementModule = itemAchievementModuleService.list(
                Wraps.lbQ(new ItemAchievementModuleEntity())
                        .in(
                                ItemAchievementModuleEntity::getUserId,
                                stuGradeList.stream()
                                        .map(StuInfoEntity::getUserId)
                                        .collect(Collectors.toList())
                        )
        );

        List<String> xAxis = new ArrayList<>();
        List<Integer> data = new ArrayList<>();
        moduleMap.entrySet()
                .stream()
                .forEach(m -> {
                    xAxis.add(m.getValue().getModuleName());

                    // 查询这个模块下有多少学生参与
                    if (CollUtil.isEmpty(clubScoreModule) && CollUtil.isEmpty(itemAchievementModule)) {
                        data.add(0);
                    } else {
                        int value  = 0;
                        if (CollUtil.isNotEmpty(itemAchievementModule)) {
                            value = itemAchievementModule.stream()
                                    .filter(i -> i.getModuleId().equals(m.getValue().getId()))
                                    .collect(Collectors.counting())
                                    .intValue();
                        }

                        if (m.getValue().getCode().eq(CreditModuleTypeEnum.COMMUNITY_WORK)) {
                            value += clubScoreModule.size();
                        }

                        data.add(value);

                    }

                });

        return GradeScoreVo.builder()
                .xAxis(xAxis)
                .gradeScoreRoList(Collections.singletonList(
                        GradeScoreRo.builder()
                                .name("参与人数")
                                .data(data)
                                .build()
                ))
                .build();
    }

    /**
     * 返回各个年级集合
     *
     * @param userId
     * @return
     */
    public List<String> gradeList(Long userId) {
        List<StuInfoEntity> stuInfoList = getStuInfoList(userId);
        if (CollUtil.isEmpty(stuInfoList)) {
            // 没有学生，默认就是四年制
            return getXAxis(4);

        }

        return getXAxis(stuInfoList.get(0).getEducationalSystem().getVariable());

    }

    private List<StuInfoEntity> getStuInfoList(Long userId) {
        // 查询学院信息
        CollegeInformationEntity collegeInformation = collegeInformationApi.info(userId).getData();
        // 查询学院下的学生
        return stuInfoApi.depStuList(DictDepartmentEnum.get(collegeInformation.getOrganizationCode())).getData();
    }

    /**
     * 封装x轴数据的方法
     */
    private List<String> getXAxis(Integer variable) {
        // 获取当前年
        LocalDate now = LocalDate.now();
        int year = now.getYear();

        // 判断是上学期还是下学期  默认是上学期
        if (now.getMonthValue() < 9) {
            year -= 1;
        }

        List<String> gradeList = new ArrayList<>();
        for (int i = 0; i < variable; i++) {
            gradeList.add((year - 0) + "级");
            year--;
        }
        return gradeList;
    }

    /**
     * 社团活动举办情况
     * @param userId
     * @return
     */
    public OrganizeActiveVo organizeActive(Long userId) {


        return OrganizeActiveVo.builder().build();
    }
}
