package com.qingfeng.cms.biz.check.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.bonus.dao.BonusScoreApplyDao;
import com.qingfeng.cms.biz.check.dao.ScoreCheckDao;
import com.qingfeng.cms.biz.check.service.ScoreCheckService;
import com.qingfeng.cms.biz.evaluation.service.EvaluationFeedbackService;
import com.qingfeng.cms.domain.bonus.entity.BonusScoreApplyEntity;
import com.qingfeng.cms.domain.check.dto.BonusScoreApplyCheckPageDTO;
import com.qingfeng.cms.domain.check.dto.ScoreCheckSaveDTO;
import com.qingfeng.cms.domain.check.entity.ScoreCheckEntity;
import com.qingfeng.cms.domain.check.enums.CheckStatusEnums;
import com.qingfeng.cms.domain.check.vo.BonusScoreApplyVo;
import com.qingfeng.cms.domain.check.vo.BonusScoreCheckPageVo;
import com.qingfeng.cms.domain.check.vo.PlanModuleVo;
import com.qingfeng.cms.domain.check.vo.ScoreCheckVo;
import com.qingfeng.cms.domain.clazz.vo.UserVo;
import com.qingfeng.cms.domain.evaluation.entity.EvaluationFeedbackEntity;
import com.qingfeng.cms.domain.item.dto.ItemAchievementModuleSaveDTO;
import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import com.qingfeng.cms.domain.plan.ro.PlanTreeRo;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.authority.entity.auth.vo.UserRoleVo;
import com.qingfeng.currency.common.enums.RoleEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.sdk.auth.role.UserRoleApi;
import com.qingfeng.sdk.messagecontrol.StuInfo.StuInfoApi;
import com.qingfeng.sdk.messagecontrol.clazz.ClazzInfoApi;
import com.qingfeng.sdk.messagecontrol.collegeinformation.CollegeInformationApi;
import com.qingfeng.sdk.planstandard.level.LevelApi;
import com.qingfeng.sdk.planstandard.module.CreditModuleApi;
import com.qingfeng.sdk.planstandard.plan.PlanApi;
import com.qingfeng.sdk.planstandard.project.ProjectApi;
import com.qingfeng.sdk.planstandard.rule.RulesApi;
import com.qingfeng.sdk.school.item.ItemAchievementModuleApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 加分审核表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
@Service
public class ScoreCheckServiceImpl extends ServiceImpl<ScoreCheckDao, ScoreCheckEntity> implements ScoreCheckService {

    @Autowired
    private EvaluationFeedbackService evaluationFeedbackService;
    @Autowired
    private BonusScoreApplyDao bonusScoreApplyDao;

    @Autowired
    private UserRoleApi userRoleApi;
    @Autowired
    private ClazzInfoApi clazzInfoApi;
    @Autowired
    private ProjectApi projectApi;
    @Autowired
    private CreditModuleApi creditModuleApi;
    @Autowired
    private LevelApi levelApi;
    @Autowired
    private RulesApi rulesApi;
    @Autowired
    private CollegeInformationApi collegeInformationApi;
    @Autowired
    private StuInfoApi stuInfoApi;
    @Autowired
    private PlanApi planApi;
    @Autowired
    private ItemAchievementModuleApi itemAchievementModuleApi;

    /**
     * 查询不同管理员需要审核的加分信息
     *
     * @param bonusScoreApplyPageDTO
     * @param userId
     * @return
     */
    @Override
    public BonusScoreCheckPageVo listBonusScore(BonusScoreApplyCheckPageDTO bonusScoreApplyPageDTO, Long userId) {
        Integer pageNo = bonusScoreApplyPageDTO.getPageNo();
        Integer pageSize = bonusScoreApplyPageDTO.getPageSize();
        bonusScoreApplyPageDTO.setPageNo((pageNo - 1) * pageSize);

        // 基础数据
        Map<Long, StuInfoEntity> stuInfoMap = Collections.emptyMap();
        List<BonusScoreApplyEntity> bonusScoreApplyList = Collections.emptyList();

        // 先要判断用户的身份
        UserRoleVo userRoleVo = userRoleApi.findRoleIdByUserId(userId).getData();
        Integer count = 0;
        if (userRoleVo.getCode().equals(RoleEnum.CLASS_GRADE.name())) {
            // 班级领导 查询班级下的学生
            List<UserVo> userVoList = clazzInfoApi.stuList().getData();
            if (CollUtil.isNotEmpty(userVoList)) {
                // 有学生再进行操作
                List<Long> userIds = userVoList.stream()
                        .map(UserVo::getId)
                        .collect(Collectors.toList());

                // 先查询用户申请的项目加分总数
                count = bonusScoreApplyDao.selectBonusScoreCheckCount(bonusScoreApplyPageDTO, userIds);
                if (count == 0) {
                    return BonusScoreCheckPageVo.builder()
                            .total(count)
                            .bonusScoreApplyList(Collections.emptyList())
                            .pageNo(pageNo)
                            .pageSize(pageSize)
                            .build();
                }

                stuInfoMap = userVoList.stream()
                        .map(UserVo::getStuInfoEntity)
                        .collect(Collectors.toMap(
                                        StuInfoEntity::getUserId,
                                        Function.identity()
                                )
                        );

                // 查询项目加分申报结果
                bonusScoreApplyList = bonusScoreApplyDao.selectBonusScoreClazzCheckList(bonusScoreApplyPageDTO, userIds);
            }
        } else if (userRoleVo.getCode().equals(RoleEnum.YUAN_LEVEL_LEADER.name())) {
            // 二级学院领导  查询学院下的学生
            List<StuInfoEntity> stuInfoList = collegeInformationApi.getUserInfoList(userId).getData();
            if (CollUtil.isNotEmpty(stuInfoList)) {
                List<Long> userIds = stuInfoList.stream()
                        .map(StuInfoEntity::getUserId)
                        .collect(Collectors.toList());

                // 先查询用户申请的项目加分总数
                count = bonusScoreApplyDao.selectBonusScoreCheckCount(bonusScoreApplyPageDTO, userIds);
                if (count == 0) {
                    return BonusScoreCheckPageVo.builder()
                            .total(count)
                            .bonusScoreApplyList(Collections.emptyList())
                            .pageNo(pageNo)
                            .pageSize(pageSize)
                            .build();
                }

                stuInfoMap = stuInfoList.stream()
                        .collect(Collectors.toMap(
                                        StuInfoEntity::getUserId,
                                        Function.identity()
                                )
                        );

                // 查询项目加分申报结果
                bonusScoreApplyList = bonusScoreApplyDao.selectBonusScoreCollegeCheckList(bonusScoreApplyPageDTO, userIds);
            }

        } else if (userRoleVo.getCode().equals(RoleEnum.STU_OFFICE_ADMIN.name())) {
            // 学生处领导  需要查询所有学生的
            count = bonusScoreApplyDao.selectAllBonusScoreCheckCount(bonusScoreApplyPageDTO);
            if (count == 0) {
                return BonusScoreCheckPageVo.builder()
                        .total(count)
                        .bonusScoreApplyList(Collections.emptyList())
                        .pageNo(pageNo)
                        .pageSize(pageSize)
                        .build();
            }

            // 查询加分申请列表
            bonusScoreApplyList = bonusScoreApplyDao.selectAllBonusScoreCheckList(bonusScoreApplyPageDTO);

            stuInfoMap = stuInfoApi.stuInfoList(bonusScoreApplyList.stream()
                            .map(BonusScoreApplyEntity::getUserId)
                            .collect(Collectors.toList())
                    ).getData()
                    .stream()
                    .collect(Collectors.toMap(
                                    StuInfoEntity::getUserId,
                                    Function.identity()
                            )
                    );

        }

        List<Long> scoreApplyIds = getScoreApplyIds(bonusScoreApplyList);
        // 查询审核和评价信息
        ConcurrentMap<Long, ScoreCheckEntity> scoreCheckMap = getScoreCheckMap(scoreApplyIds);
        Map<Long, EvaluationFeedbackEntity> evaluationFeedbackMap = getEvaluationFeedbackMap(scoreApplyIds);

        // 查询模块信息
        Map<Long, CreditModuleEntity> moduleMap = getModuleMap(bonusScoreApplyList);
        //查询项目信息
        Map<Long, ProjectEntity> projectMap = getProjectMap(bonusScoreApplyList);
        // 查询等级信息
        Map<Long, LevelEntity> levelMap = getLevelMap(bonusScoreApplyList);
        // 查询学分细则信息
        Map<Long, CreditRulesEntity> rulesMap = getRulesMap(bonusScoreApplyList);

        // 封装数据
        List<BonusScoreApplyVo> bonusScoreApplyVoList = getBonusScoreApplyVoList(
                bonusScoreApplyList,
                scoreCheckMap,
                evaluationFeedbackMap,
                moduleMap,
                projectMap,
                levelMap,
                rulesMap,
                stuInfoMap,
                userRoleVo
        );

        return BonusScoreCheckPageVo.builder()
                .total(count)
                .bonusScoreApplyList(bonusScoreApplyVoList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 封装项目学分申请Id
     *
     * @param bonusScoreApplyList
     * @return
     */
    private List<Long> getScoreApplyIds(List<BonusScoreApplyEntity> bonusScoreApplyList) {
        return bonusScoreApplyList.stream()
                .map(BonusScoreApplyEntity::getId)
                .collect(Collectors.toList());
    }

    /**
     * 查询项目申请审核信息
     *
     * @param scoreApplyIds
     * @return
     */
    private ConcurrentMap<Long, ScoreCheckEntity> getScoreCheckMap(List<Long> scoreApplyIds) {
        return baseMapper.selectList(Wraps.lbQ(new ScoreCheckEntity())
                        .in(ScoreCheckEntity::getScoreApplyId, scoreApplyIds))
                .stream()
                .collect(Collectors.toConcurrentMap(
                                ScoreCheckEntity::getScoreApplyId,
                                Function.identity()
                        )
                );
    }

    /**
     * 查询项目申请评价信息
     *
     * @param scoreApplyIds
     * @return
     */
    private Map<Long, EvaluationFeedbackEntity> getEvaluationFeedbackMap(List<Long> scoreApplyIds) {
        List<EvaluationFeedbackEntity> evaluationFeedbackList = evaluationFeedbackService.list(Wraps.lbQ(new EvaluationFeedbackEntity())
                .in(EvaluationFeedbackEntity::getScoreApplyId, scoreApplyIds));
        Map<Long, EvaluationFeedbackEntity> evaluationFeedbackMap = Collections.emptyMap();
        if (CollUtil.isNotEmpty(evaluationFeedbackList)) {
            evaluationFeedbackMap = evaluationFeedbackList.stream()
                    .collect(Collectors.toMap(
                                    EvaluationFeedbackEntity::getScoreApplyId,
                                    Function.identity()
                            )
                    );
        }
        return evaluationFeedbackMap;
    }

    /**
     * 查询模块信息
     *
     * @param bonusScoreApplyList
     * @return
     */
    private Map<Long, CreditModuleEntity> getModuleMap(List<BonusScoreApplyEntity> bonusScoreApplyList) {
        return creditModuleApi.moduleByIds(bonusScoreApplyList.stream()
                        .map(BonusScoreApplyEntity::getModuleId)
                        .collect(Collectors.toList()
                        )
                )
                .getData()
                .stream()
                .collect(Collectors.toMap(
                        CreditModuleEntity::getId,
                        Function.identity()
                ));
    }

    /**
     * 查询项目信息
     *
     * @param bonusScoreApplyList
     * @return
     */
    private Map<Long, ProjectEntity> getProjectMap(List<BonusScoreApplyEntity> bonusScoreApplyList) {
        return projectApi.projectInfoByIds(bonusScoreApplyList.stream()
                        .map(BonusScoreApplyEntity::getProjectId)
                        .collect(Collectors.toList()
                        )
                ).getData()
                .stream()
                .collect(Collectors.toMap(
                        ProjectEntity::getId,
                        Function.identity()
                ));
    }

    /**
     * 查询等级信息
     *
     * @param bonusScoreApplyList
     * @return
     */
    private Map<Long, LevelEntity> getLevelMap(List<BonusScoreApplyEntity> bonusScoreApplyList) {
        return levelApi.levelInfoByIds(bonusScoreApplyList.stream()
                        .map(BonusScoreApplyEntity::getLevelId)
                        .collect(Collectors.toList()
                        )
                ).getData()
                .stream()
                .collect(Collectors.toMap(
                        LevelEntity::getId,
                        Function.identity()
                ));
    }

    /**
     * 查询学分细则信息
     *
     * @param bonusScoreApplyList
     * @return
     */
    private Map<Long, CreditRulesEntity> getRulesMap(List<BonusScoreApplyEntity> bonusScoreApplyList) {
        return rulesApi.ruleInfoByIds(bonusScoreApplyList.stream()
                        .map(BonusScoreApplyEntity::getCreditRulesId)
                        .collect(Collectors.toList()
                        )
                ).getData()
                .stream()
                .collect(Collectors.toMap(
                        CreditRulesEntity::getId,
                        Function.identity()
                ));
    }

    private List<BonusScoreApplyVo> getBonusScoreApplyVoList(List<BonusScoreApplyEntity> bonusScoreApplyList,
                                                             ConcurrentMap<Long, ScoreCheckEntity> scoreCheckMap,
                                                             Map<Long, EvaluationFeedbackEntity> evaluationFeedbackMap,
                                                             Map<Long, CreditModuleEntity> moduleMap,
                                                             Map<Long, ProjectEntity> projectMap,
                                                             Map<Long, LevelEntity> levelMap,
                                                             Map<Long, CreditRulesEntity> rulesMap,
                                                             Map<Long, StuInfoEntity> stuInfoMap,
                                                             UserRoleVo userRoleVo) {
        return bonusScoreApplyList.stream()
                .map(bonusScoreApply -> {
                            ScoreCheckEntity scoreCheck = scoreCheckMap.get(bonusScoreApply.getId());
                            ScoreCheckVo scoreCheckVo = ScoreCheckVo.builder()
                                    .id(scoreCheck.getId())
                                    .scoreApplyId(scoreCheck.getScoreApplyId())
                                    .classStatus(scoreCheck.getClassStatus())
                                    .classFeedback(scoreCheck.getClassFeedback())
                                    .collegeStatus(scoreCheck.getCollegeStatus())
                                    .collegeFeedback(scoreCheck.getCollegeFeedback())
                                    .studentOfficeStatus(scoreCheck.getStudentOfficeStatus())
                                    .studentOfficeFeedback(scoreCheck.getStudentOfficeFeedback())
                                    .build();
                            if (userRoleVo.getCode().equals(RoleEnum.CLASS_GRADE.name())) {
                                scoreCheckVo.setStatus(scoreCheck.getClassStatus());
                                scoreCheckVo.setFeedback(scoreCheck.getClassFeedback());
                            } else if (userRoleVo.getCode().equals(RoleEnum.YUAN_LEVEL_LEADER.name())) {
                                scoreCheckVo.setStatus(scoreCheck.getCollegeStatus());
                                scoreCheckVo.setFeedback(scoreCheck.getCollegeFeedback());
                            } else if (userRoleVo.getCode().equals(RoleEnum.STU_OFFICE_ADMIN.name())) {
                                scoreCheckVo.setStatus(scoreCheck.getStudentOfficeStatus());
                                scoreCheckVo.setFeedback(scoreCheck.getStudentOfficeFeedback());
                            }
                            return BonusScoreApplyVo.builder()
                                    .id(bonusScoreApply.getId())
                                    .userId(bonusScoreApply.getUserId())
                                    .moduleName(moduleMap.get(bonusScoreApply.getModuleId()).getModuleName())
                                    .projectName(projectMap.get(bonusScoreApply.getProjectId()).getProjectName())
                                    .levelName(levelMap.get(bonusScoreApply.getLevelId()).getLevelContent())
                                    .score(rulesMap.get(bonusScoreApply.getCreditRulesId()).getScore())
                                    .scoreGrade(rulesMap.get(bonusScoreApply.getCreditRulesId()).getScoreGrade())
                                    .conditions(rulesMap.get(bonusScoreApply.getCreditRulesId()).getConditions())
                                    .supportMaterial(bonusScoreApply.getSupportMaterial())
                                    .bonusStatus(bonusScoreApply.getBonusStatus())
                                    .year(bonusScoreApply.getYear())
                                    .schoolYear(bonusScoreApply.getSchoolYear())
                                    .scoreCheck(scoreCheckVo)
                                    .evaluationFeedback(evaluationFeedbackMap.getOrDefault(bonusScoreApply.getId(), null))
                                    .stuInfoEntity(stuInfoMap.get(bonusScoreApply.getUserId()))
                                    .build();
                        }
                )
                .collect(Collectors.toList());
    }

    /**
     * 查询方案模块信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<PlanModuleVo> findPlanModuleList(Long userId) {
        // 先要判断用户的身份
        UserRoleVo userRoleVo = userRoleApi.findRoleIdByUserId(userId).getData();
        if (userRoleVo.getCode().equals(RoleEnum.CLASS_GRADE.name())) {
            // 班级信息只查询班级下的
            List<UserVo> userVoList = clazzInfoApi.stuList().getData();
            if (CollUtil.isNotEmpty(userVoList)) {
                PlanEntity plan = planApi.getPlanByUserId(userVoList.get(0).getId()).getData();
                // 查询模块信息
                List<PlanModuleVo> planModuleVoList = creditModuleApi.findModuleListByPlanId(plan.getId())
                        .getData()
                        .stream()
                        .map(m -> PlanModuleVo.builder()
                                .value(m.getId())
                                .label(m.getModuleName())
                                .build()
                        )
                        .collect(Collectors.toList());

                return Collections.singletonList(
                        PlanModuleVo.builder()
                                .value(plan.getId())
                                .label(plan.getGrade() + "（" +
                                        (plan.getApplicationObject() == 1 ? "本科" : "专科")
                                        + "）" + plan.getPlanName())
                                .children(planModuleVoList)
                                .build()
                );
            }
        } else {
            // 学院以上的就直接查询全部的
            List<PlanTreeRo> planTreeRoList = creditModuleApi.findPlanAndModule().getData();

            return planTreeRoList.stream()
                    .map(planTreeRo -> PlanModuleVo.builder()
                            .value(planTreeRo.getId())
                            .label(planTreeRo.getPlanModuleLabel())
                            .children(CollUtil.isEmpty(planTreeRo.getChildren())
                                    ? null : planTreeRo.getChildren()
                                    .stream()
                                    .map(m -> PlanModuleVo.builder()
                                            .value(m.getId())
                                            .label(m.getPlanModuleLabel())
                                            .build()
                                    )
                                    .collect(Collectors.toList())
                            )
                            .build()
                    )
                    .collect(Collectors.toList()
                    );
        }

        return Collections.emptyList();
    }

    /**
     * 项目加分申请审核
     * 1. 要判断用户角色：班级、学院、学生处
     * 2. 审核加分环节，必须是在最后一级学生处审核完成后进行加分（调用加分服务进行加分）
     * 3. 一旦有审核未通过的环节，需要给学生发送通知
     *
     * @param scoreCheckSaveDTO
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void saveCheck(ScoreCheckSaveDTO scoreCheckSaveDTO, Long userId) {
        // 查询出之前的原始信息
        ScoreCheckEntity scoreCheck = baseMapper.selectOne(
                Wraps.lbQ(new ScoreCheckEntity())
                        .eq(ScoreCheckEntity::getScoreApplyId, scoreCheckSaveDTO.getScoreApplyId())
        );
        // 先要判断用户的身份
        UserRoleVo userRoleVo = userRoleApi.findRoleIdByUserId(userId).getData();
        if (userRoleVo.getCode().equals(RoleEnum.CLASS_GRADE.name())) {
            // 班级领导
            scoreCheck.setClassStatus(scoreCheckSaveDTO.getStatus());
            scoreCheck.setClassFeedback(scoreCheckSaveDTO.getFeedback());
        } else if (userRoleVo.getCode().equals(RoleEnum.YUAN_LEVEL_LEADER.name())) {
            // 学院领导
            scoreCheck.setCollegeStatus(scoreCheckSaveDTO.getStatus());
            scoreCheck.setCollegeFeedback(scoreCheckSaveDTO.getFeedback());
        } else if (userRoleVo.getCode().equals(RoleEnum.STU_OFFICE_ADMIN.name())) {
            // 学生处领导
            scoreCheck.setStudentOfficeStatus(scoreCheckSaveDTO.getStatus());
            scoreCheck.setStudentOfficeFeedback(scoreCheckSaveDTO.getFeedback());

            if (scoreCheckSaveDTO.getStatus().eq(CheckStatusEnums.COMPLETE)) {
                BonusScoreApplyEntity bonusScore = bonusScoreApplyDao.selectById(scoreCheckSaveDTO.getScoreApplyId());
                CreditModuleEntity creditModule = (CreditModuleEntity) creditModuleApi.info(bonusScore.getModuleId()).getData();
                CreditRulesEntity rules = rulesApi.ruleInfoByIds(Collections.singletonList(
                                        bonusScore.getCreditRulesId()
                                )
                        ).getData()
                        .get(0);
                // 审核通过，调用加分的服务进行加分
                itemAchievementModuleApi.save(
                        ItemAchievementModuleSaveDTO.builder()
                                .userId(bonusScore.getUserId())
                                .moduleId(bonusScore.getModuleId())
                                .moduleCode(creditModule.getCode().getCode())
                                .projectId(bonusScore.getProjectId())
                                .levelId(bonusScore.getLevelId())
                                .creditRulesId(bonusScore.getCreditRulesId())
                                .score(ObjectUtil.isEmpty(scoreCheckSaveDTO.getScore()) ?
                                        BigDecimal.valueOf(rules.getScore()) : scoreCheckSaveDTO.getScore())
                                .build()
                );
            }
        }

        // 进行修改
        baseMapper.updateById(scoreCheck);

        // TODO 如果审核是不通过的，那么需要发送邮件给对应的学生进行修改
        if(scoreCheckSaveDTO.getStatus().eq(CheckStatusEnums.FAIL)) {
//            sendMsg(scoreCheckSaveDTO, userRoleVo);
        }
    }

    /*private void sendMsg(ScoreCheckSaveDTO scoreCheckSaveDTO, UserRoleVo userRoleVo) {
        User user = userRoleApi.findStuClazzInfo().getData();

        // 查询模块等信息
        CreditModuleEntity creditModule = (CreditModuleEntity) creditModuleApi.info(
                        bonusScoreApply.getModuleId()
                )
                .getData();

        ProjectEntity project = projectApi.findInfoById(bonusScoreApply.getProjectId())
                .getData();

        LevelEntity level = levelApi.levelInfoById(bonusScoreApply.getLevelId())
                .getData();


        String title = "";
        String body = "";
        if (ObjectUtil.isNotEmpty(bonusScoreApply.getId())) {
            title = "模块《"+creditModule.getModuleName()+"》加分申报修改待审核通知";
            body = "亲爱的班级负责人：\r\n       "
                    + "模块《"+creditModule.getModuleName()+"》->"
                    + "项目：（" + project.getProjectName() +"）->"
                    + "等级：（" + level.getLevelContent() +"）->"
                    + "加分申请已经修改，并申请成功，请尽早进行审核，以免影响加分进度！";
        } else {
            title = "模块《"+creditModule.getModuleName()+"》加分申报待审核通知";
            body = "亲爱的班级负责人：\r\n       "
                    + "模块《"+creditModule.getModuleName()+"》->"
                    + "项目：（" + project.getProjectName() +"）->"
                    + "等级：（" + level.getLevelContent() +"）->"
                    + "加分申请已经申请成功，请尽早进行审核，以免影响加分进度！";
        }

        if (StrUtil.isNotBlank(user.getEmail())) {
            //有邮箱就先向邮箱中发送消息   使用消息队列进行发送  失败重试三次
            try {
                rabbitSendMsg.sendEmail(objectMapper.writeValueAsString(EmailEntity.builder()
                        .email(user.getEmail())
                        .title(title)
                        .body(body)
                        .key("bonus.item.email")
                        .build()), "bonus.item.email");

                // 进行消息存储
                //将消息通知写入数据库
                R r = newsNotifyApi.save(NewsNotifySaveDTO.builder()
                        .userId(user.getId())
                        .newsType(NewsTypeEnum.MAILBOX)
                        .newsTitle(title)
                        .newsContent(body)
                        .isSee(IsSeeEnum.IS_NOT_VIEWED)
                        .build());

                if (r.getIsError()) {
                    throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "消息通知保存失败！");
                }
            } catch (JsonProcessingException e) {
                throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "项目加分申请审核异常");
            }

        } else {
            // TODO 短信发送
        }
    }*/
}