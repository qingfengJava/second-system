package com.qingfeng.cms.biz.statistics.service;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.qingfeng.cms.biz.club.service.ClubScoreModuleService;
import com.qingfeng.cms.biz.item.service.ItemAchievementModuleService;
import com.qingfeng.cms.biz.total.service.StudentScoreTotalService;
import com.qingfeng.cms.domain.apply.entity.ApplyCheckEntity;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.apply.enums.ActiveLevelEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveScaleEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveStatusEnum;
import com.qingfeng.cms.domain.apply.enums.ApplyCheckStatusEnum;
import com.qingfeng.cms.domain.clazz.vo.UserVo;
import com.qingfeng.cms.domain.club.entity.ClubScoreModuleEntity;
import com.qingfeng.cms.domain.college.entity.CollegeInformationEntity;
import com.qingfeng.cms.domain.dict.enums.DictDepartmentEnum;
import com.qingfeng.cms.domain.item.entity.ItemAchievementModuleEntity;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.module.enums.CreditModuleTypeEnum;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
import com.qingfeng.cms.domain.sign.entity.ActiveSignEntity;
import com.qingfeng.cms.domain.statistics.ro.GradeScoreRo;
import com.qingfeng.cms.domain.statistics.ro.StuSemesterCreditsRo;
import com.qingfeng.cms.domain.statistics.vo.ClassModuleVo;
import com.qingfeng.cms.domain.statistics.vo.ClazzCreditsVo;
import com.qingfeng.cms.domain.statistics.vo.CommunitySituationVo;
import com.qingfeng.cms.domain.statistics.vo.GradeScoreVo;
import com.qingfeng.cms.domain.statistics.vo.OrganizeActiveVo;
import com.qingfeng.cms.domain.statistics.vo.StuSemesterCreditsVo;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.cms.domain.total.entity.StudentScoreTotalEntity;
import com.qingfeng.cms.domain.total.vo.StuModuleDataAnalysisVo;
import com.qingfeng.currency.common.enums.RoleEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.sdk.active.apply.ApplyApi;
import com.qingfeng.sdk.active.apply_check.ApplyCheckApi;
import com.qingfeng.sdk.active.sign.ActiveSignApi;
import com.qingfeng.sdk.auth.role.RoleApi;
import com.qingfeng.sdk.messagecontrol.StuInfo.StuInfoApi;
import com.qingfeng.sdk.messagecontrol.clazz.ClazzInfoApi;
import com.qingfeng.sdk.messagecontrol.collegeinformation.CollegeInformationApi;
import com.qingfeng.sdk.messagecontrol.organize.OrganizeInfoApi;
import com.qingfeng.sdk.planstandard.module.CreditModuleApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
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
    @Autowired
    private ApplyApi applyApi;
    @Autowired
    private ApplyCheckApi applyCheckApi;
    @Autowired
    private ActiveSignApi activeSignApi;
    @Autowired
    private RoleApi roleApi;
    @Autowired
    private OrganizeInfoApi organizeInfoApi;

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
                        int value = 0;
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
     *
     * @param userId
     * @return
     */
    public OrganizeActiveVo organizeActive(Long userId) {
        // 查询目前该社团申请的所有活动
        List<ApplyEntity> applyEntityList = applyApi.getUserActivityList(userId).getData();
        if (CollUtil.isEmpty(applyEntityList)) {
            List<Integer> asList = Arrays.asList(0, 0, 0);
            // 为空直接返回
            return OrganizeActiveVo.builder()
                    .actNum(asList)
                    .bigActNum(asList)
                    .mediumActNum(asList)
                    .smallActNum(asList)
                    .applyActNum(asList)
                    .inActNum(asList)
                    .completeActNum(asList)
                    .abandonActNum(asList)
                    .build();
        }

        // 过滤所有的大型活动
        List<ApplyEntity> bigActList = applyEntityList.stream()
                .filter(a -> a.getActiveScale().eq(ActiveScaleEnum.BIG))
                .collect(Collectors.toList());
        // 过滤所有的中型活动
        List<ApplyEntity> mediumActList = applyEntityList.stream()
                .filter(a -> a.getActiveScale().eq(ActiveScaleEnum.MIDDLE))
                .collect(Collectors.toList());
        // 过滤所有的小型项目
        List<ApplyEntity> smallActList = applyEntityList.stream()
                .filter(a -> a.getActiveScale().eq(ActiveScaleEnum.SMALL))
                .collect(Collectors.toList());
        // 过滤所有申请中的活动 只要没有活动状态的都是在申请中的活动
        List<ApplyEntity> applyActList = applyEntityList.stream()
                .filter(a -> ObjectUtil.isEmpty(a.getActiveStatus()))
                .collect(Collectors.toList());

        // 查询所有进行中的活动
        List<ApplyEntity> inActList = applyEntityList.stream()
                .filter(a ->
                        ObjectUtil.isNotEmpty(a.getActiveStatus())
                                && (a.getActiveStatus().equals(ActiveStatusEnum.INIT)
                                || a.getActiveStatus().equals(ActiveStatusEnum.HAVING))
                )
                .collect(Collectors.toList());

        // 查询所有已完成的活动
        List<ApplyEntity> completeActList = applyEntityList.stream()
                .filter(a -> ObjectUtil.isNotEmpty(a.getActiveStatus()) && a.getActiveStatus().equals(ActiveStatusEnum.COMPLETE))
                .collect(Collectors.toList());

        // 查询所有废弃的活动
        List<ApplyEntity> abandonActList = applyEntityList.stream()
                .filter(a -> ObjectUtil.isNotEmpty(a.getActiveStatus()) && a.getActiveStatus().equals(ActiveStatusEnum.ABANDONMENT))
                .collect(Collectors.toList());

        // 查询所有已经终审的活动
        List<ApplyCheckEntity> applyCheckList = applyCheckApi.findByApplyIds(
                applyEntityList.stream()
                        .map(ApplyEntity::getId)
                        .collect(Collectors.toList())
        ).getData();
        // 查询所有已完成，但终审为通过的活动
        Set<Long> ids = new HashSet<>();
        if (ObjectUtil.isNotEmpty(applyCheckList)) {
            ids = applyCheckList.stream()
                    .filter(a -> a.getCheckStatus().equals(ApplyCheckStatusEnum.IS_NOT_PASSED))
                    .map(ApplyCheckEntity::getApplyId)
                    .collect(Collectors.toSet());
        }
        Set<Long> finalIds = ids;
        List<ApplyEntity> isNotPassList = completeActList.stream()
                .filter(c -> finalIds.contains(c.getId()))
                .collect(Collectors.toList());

        return OrganizeActiveVo.builder()
                .actNum(getActiveNum(applyEntityList))
                .bigActNum(getActiveNum(bigActList))
                .mediumActNum(getActiveNum(mediumActList))
                .smallActNum(getActiveNum(smallActList))
                .applyActNum(getActiveNum(applyActList))
                .inActNum(getActiveNum(inActList))
                .completeActNum(getActiveNum(completeActList))
                .abandonActNum(getActiveNum(abandonActList))
                .applyNotPassActNum(getActiveNum(isNotPassList))
                .build();
    }

    /**
     * 封装总活动中的校级活动，院级活动，一般活动
     *
     * @param applyEntityList
     * @return
     */
    private List<Integer> getActiveNum(List<ApplyEntity> applyEntityList) {
        if (CollUtil.isEmpty(applyEntityList)) {
            return Arrays.asList(0, 0, 0);
        }
        int schoolItemNum = applyEntityList.stream()
                .filter(a -> a.getActiveLevel().eq(ActiveLevelEnum.SCHOOL_ITEMS))
                .collect(Collectors.counting())
                .intValue();

        int instituteItemNum = applyEntityList.stream()
                .filter(a -> a.getActiveLevel().eq(ActiveLevelEnum.INSTITUTE_ITEMS))
                .collect(Collectors.counting())
                .intValue();

        int generalItemNum = applyEntityList.stream()
                .filter(a -> a.getActiveLevel().eq(ActiveLevelEnum.GENERAL_ITEMS))
                .collect(Collectors.counting())
                .intValue();

        return Arrays.asList(schoolItemNum, instituteItemNum, generalItemNum);
    }

    /**
     * 每学期社团活动评分详情
     *
     * @param schoolYear
     * @param userId
     * @return
     */
    public List<Integer> activityScore(String schoolYear, Long userId) {
        List<ApplyEntity> applyList = applyApi.getUserActivityListByuserIdAndSchoolYear(userId, schoolYear).getData();

        if (CollUtil.isEmpty(applyList)) {
            return Arrays.asList(0, 0, 0, 0, 0);
        }

        List<ActiveSignEntity> activeSignList = activeSignApi.findByApplyIdsList(
                applyList.stream()
                        .map(ApplyEntity::getId)
                        .collect(Collectors.toList())
        ).getData();

        if (CollUtil.isEmpty(activeSignList)) {
            return Arrays.asList(0, 0, 0, 0, 0);
        }

        // 活动个数
        return Arrays.asList(
                getActNum(activeSignList, 1),
                getActNum(activeSignList, 2),
                getActNum(activeSignList, 3),
                getActNum(activeSignList, 4),
                getActNum(activeSignList, 5)
        );
    }

    private int getActNum(List<ActiveSignEntity> activeSignList, int i) {
        if (CollUtil.isEmpty(activeSignList)) {
            return 0;
        }
        return activeSignList.stream()
                .filter(a -> ObjectUtil.isNotEmpty(a.getEvaluationValue()) && a.getEvaluationValue() == i)
                .collect(Collectors.counting())
                .intValue();
    }

    /**
     * 查询每个社团的活动举办情况
     *
     * @param academicYear
     * @return
     */
    public CommunitySituationVo communitySituation(String academicYear) {
        // 查询所有的社团用户Id
        List<Long> userIds = roleApi.findUserIdByCode(
                new String[]{
                        RoleEnum.SOCIAL_ORGANIZATION.name()
                }
        ).getData();

        // 查询所有的社团组织
        List<OrganizeInfoEntity> organizeInfoList = organizeInfoApi.infoList(userIds).getData();
        // 如果社团组织不存在，直接返回数据
        if (CollUtil.isEmpty(organizeInfoList)) {
            return CommunitySituationVo.builder()
                    .xData(Collections.emptyList())
                    .actNum(Collections.emptyList())
                    .bigActNum(Collections.emptyList())
                    .mediumActNum(Collections.emptyList())
                    .smallActNum(Collections.emptyList())
                    .build();
        }

        // 封装社团信息Map
        Map<Long, OrganizeInfoEntity> organizeInfoMap = organizeInfoList.stream()
                .collect(Collectors.toMap(
                        OrganizeInfoEntity::getUserId,
                        Function.identity()
                ));

        // 查询所有的社团活动信息，根据学年去查询
        List<ApplyEntity> applyEntityList = applyApi.getActivityListByUserIdsAndSchoolYear(userIds, academicYear).getData();

        Map<Long, List<ApplyEntity>> applyMap = new HashMap<>(16);
        if (CollUtil.isNotEmpty(applyEntityList)) {
            applyMap = applyEntityList.stream()
                    .collect(Collectors.groupingBy(
                            ApplyEntity::getApplyUserId
                    ));
        }

        List<String> xData = new ArrayList<>();
        List<Integer> actNum = new ArrayList<>();
        List<Integer> bigActNum = new ArrayList<>();
        List<Integer> mediumActNum = new ArrayList<>();
        List<Integer> smallActNum = new ArrayList<>();
        // 社团信息一定会有，不用去排除
        Map<Long, List<ApplyEntity>> finalApplyMap = applyMap;
        userIds.forEach(id -> {
            OrganizeInfoEntity organizeInfoEntity = organizeInfoMap.get(id);
            xData.add(organizeInfoEntity.getOrganizeName());

            List<ApplyEntity> applyList = finalApplyMap.getOrDefault(id, new ArrayList<ApplyEntity>());

            actNum.add(applyList.size());
            bigActNum.add(getValue(applyList, ActiveScaleEnum.BIG));
            mediumActNum.add(getValue(applyList, ActiveScaleEnum.MIDDLE));
            smallActNum.add(getValue(applyList, ActiveScaleEnum.SMALL));
        });

        return CommunitySituationVo.builder()
                .xData(xData)
                .actNum(actNum)
                .bigActNum(bigActNum)
                .mediumActNum(mediumActNum)
                .smallActNum(smallActNum)
                .build();
    }

    private int getValue(List<ApplyEntity> applyList, ActiveScaleEnum middle) {
        if (CollUtil.isEmpty(applyList)) {
            return 0;
        }
        return applyList.stream()
                .filter(a -> a.getActiveScale().equals(middle))
                .collect(Collectors.counting())
                .intValue();
    }

    /**
     * 根据社团名和学年查询社团活动质量分析情况
     *
     * @param orgName
     * @param schoolYear
     * @return
     */
    public List<Integer> evaluationQuality(String orgName, String schoolYear) {
        OrganizeInfoEntity organizeInfoEntity = organizeInfoApi.findInfoByName(orgName).getData();
        // 根据社团组织Id和学年查询举办的活动信息
        List<ApplyEntity> applyEntityList = applyApi.getActivityListByUserIdsAndSchoolYear(
                Collections.singletonList(organizeInfoEntity.getUserId()),
                schoolYear
        ).getData();

        if (CollUtil.isEmpty(applyEntityList)) {
            return Arrays.asList(0, 0, 0, 0, 0);
        }

        // 查询学生报名参与并评价的活动信息
        List<ActiveSignEntity> signList = activeSignApi.findByApplyIdsList(
                applyEntityList.stream()
                        .map(ApplyEntity::getId)
                        .collect(Collectors.toList())
        ).getData();

        return Arrays.asList(
                getActNum(signList, 1),
                getActNum(signList, 2),
                getActNum(signList, 3),
                getActNum(signList, 4),
                getActNum(signList, 5)
        );
    }

    /**
     * 查询所有学院的各年级修读情况
     * @param dictDep
     * @return
     */
    public GradeScoreVo findDepAllModule(String dictDep) {
        List<Long> depIds = collegeInformationApi.depInfo(dictDep).getData();
        return this.gradeScore(CollUtil.isEmpty(depIds) ? -1L : depIds.get(0));
    }

    /**
     * 返回学院对应的年级信息
     * @param depName
     * @return
     */
    public List<String> gradeListByDepName(String depName) {
        List<Long> depIds = collegeInformationApi.depInfo(depName).getData();
        return this.gradeList(CollUtil.isEmpty(depIds) ? -1L : depIds.get(0));
    }

    /**
     * 查询学院个年级学生模块修读情况
     * @param depName
     * @param grade
     * @return
     */
    public GradeScoreVo findDepStuByDepName(String depName, String grade) {
        List<Long> depIds = collegeInformationApi.depInfo(depName).getData();
        return this.gradeModule(CollUtil.isEmpty(depIds) ? -1L : depIds.get(0), grade);
    }
}
