package com.qingfeng.cms.biz.bonus.service.impl;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.ObjectUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.cms.biz.bonus.dao.BonusScoreApplyDao;
import com.qingfeng.cms.biz.bonus.service.BonusScoreApplyService;
import com.qingfeng.cms.biz.check.service.ScoreCheckService;
import com.qingfeng.cms.biz.evaluation.service.EvaluationFeedbackService;
import com.qingfeng.cms.biz.service.producer.RabbitSendMsg;
import com.qingfeng.cms.domain.bonus.dto.BonusScoreApplyPageDTO;
import com.qingfeng.cms.domain.bonus.dto.BonusScoreApplySaveDTO;
import com.qingfeng.cms.domain.bonus.dto.BonusScoreApplyUpdateDTO;
import com.qingfeng.cms.domain.bonus.entity.BonusScoreApplyEntity;
import com.qingfeng.cms.domain.bonus.enums.BonusStatusEnums;
import com.qingfeng.cms.domain.bonus.vo.BonusScoreApplyVo;
import com.qingfeng.cms.domain.bonus.vo.BonusScorePageVo;
import com.qingfeng.cms.domain.check.entity.ScoreCheckEntity;
import com.qingfeng.cms.domain.check.enums.CheckStatusEnums;
import com.qingfeng.cms.domain.dict.enums.DictDepartmentEnum;
import com.qingfeng.cms.domain.evaluation.entity.EvaluationFeedbackEntity;
import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.enums.IsSeeEnum;
import com.qingfeng.cms.domain.news.enums.NewsTypeEnum;
import com.qingfeng.cms.domain.project.dto.ProjectQueryDTO;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.cms.domain.project.enums.ProjectCheckEnum;
import com.qingfeng.cms.domain.project.vo.ProjectListVo;
import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.auth.role.UserRoleApi;
import com.qingfeng.sdk.messagecontrol.StuInfo.StuInfoApi;
import com.qingfeng.sdk.messagecontrol.news.NewsNotifyApi;
import com.qingfeng.sdk.oss.file.FileOssApi;
import com.qingfeng.sdk.planstandard.level.LevelApi;
import com.qingfeng.sdk.planstandard.module.CreditModuleApi;
import com.qingfeng.sdk.planstandard.project.ProjectApi;
import com.qingfeng.sdk.planstandard.rule.RulesApi;
import com.qingfeng.sdk.sms.email.domain.EmailEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentMap;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 加分申报表（提交加分细则申请）
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
@Service
public class BonusScoreApplyServiceImpl extends ServiceImpl<BonusScoreApplyDao, BonusScoreApplyEntity> implements BonusScoreApplyService {

    @Autowired
    private ScoreCheckService scoreCheckService;
    @Autowired
    private EvaluationFeedbackService evaluationFeedbackService;
    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private ProjectApi projectApi;
    @Autowired
    private StuInfoApi stuInfoApi;
    @Autowired
    private CreditModuleApi creditModuleApi;
    @Autowired
    private LevelApi levelApi;
    @Autowired
    private RulesApi rulesApi;
    @Autowired
    private FileOssApi fileOssApi;
    @Autowired
    private RabbitSendMsg rabbitSendMsg;
    @Autowired
    private UserRoleApi userRoleApi;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private NewsNotifyApi newsNotifyApi;

    /**
     * 分页查询加分申报表
     *
     * @param bonusScoreApplyPageDTO
     * @param userId
     * @return
     */
    @Override
    public BonusScorePageVo findBonusScorePage(BonusScoreApplyPageDTO bonusScoreApplyPageDTO, Long userId) {
        Integer pageNo = bonusScoreApplyPageDTO.getPageNo();
        Integer pageSize = bonusScoreApplyPageDTO.getPageSize();
        Integer total = baseMapper.findBonusScorePageCount(bonusScoreApplyPageDTO, userId);
        if (total == 0) {
            return BonusScorePageVo.builder()
                    .total(total)
                    .bonusScoreApplyList(Collections.emptyList())
                    .pageNo(pageNo)
                    .pageSize(pageSize)
                    .build();
        }
        bonusScoreApplyPageDTO.setPageNo((pageNo - 1) * pageSize);
        List<BonusScoreApplyEntity> bonusScoreApplyList = baseMapper.findBonusScorePage(bonusScoreApplyPageDTO, userId);

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
                rulesMap
        );

        return BonusScorePageVo.builder()
                .total(total)
                .bonusScoreApplyList(bonusScoreApplyVoList)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .build();
    }

    /**
     * 根据模块Id查询项目等级学分信息
     *
     * @param moduleId
     * @param userId
     * @return
     */
    @Override
    public List<ProjectListVo> projectList(Long moduleId, Long userId) {
        List<ProjectListVo> projectList = projectApi.list(
                ProjectQueryDTO.builder()
                        .moduleId(moduleId)
                        .isCheck(ProjectCheckEnum.IS_FINISHED)
                        .isEnable(1)
                        .build()
        ).getData();

        // 查询用户所属的学院信息
        StuInfoEntity stuInfo = stuInfoApi.info(userId).getData();
        // 过滤学校和自己学院下的项目信息
        List<ProjectListVo> projectVoList = projectList.stream()
                .filter(p -> p.getDepartment().equals(DictDepartmentEnum.PZHU.name())
                        || p.getDepartment().equals(stuInfo.getDepartment().name()))
                .collect(Collectors.toList());

        // TODO 是否排除已经报名的项目等级信息
        /*List<BonusScoreApplyEntity> bonusScoreApplyList = baseMapper.selectList(Wraps.lbQ(new BonusScoreApplyEntity())
                .eq(BonusScoreApplyEntity::getUserId, userId));

        if (CollUtil.isEmpty(bonusScoreApplyList)) {
            return projectVoList;
        }*/

        return projectVoList;
    }

    /**
     * 项目加分申报
     *
     * @param bonusScoreApplySaveDTO
     * @param userId
     */
    @Override
    public void saveBonusScoreApply(BonusScoreApplySaveDTO bonusScoreApplySaveDTO, Long userId) {
        BonusScoreApplyEntity bonusScoreApply = dozerUtils.map2(bonusScoreApplySaveDTO, BonusScoreApplyEntity.class);
        bonusScoreApply.setUserId(userId);
        bonusScoreApply.setBonusStatus(BonusStatusEnums.HAVING);
        baseMapper.insert(bonusScoreApply);

        // 保存一条初始的审核记录
        ScoreCheckEntity scoreCheck = ScoreCheckEntity.builder()
                .classStatus(CheckStatusEnums.INIT)
                .collegeStatus(CheckStatusEnums.INIT)
                .studentOfficeStatus(CheckStatusEnums.INIT)
                .build();
        scoreCheck.setScoreApplyId(bonusScoreApply.getId());
        scoreCheckService.save(scoreCheck);

        //需要通知班级管理员进行审核
        sendMasg(bonusScoreApply);
    }

    /**
     * 取消项目加分申报
     *
     * @param id
     */
    @Override
    public void cancelBonusById(Long id) {
        // 取消项目的加分申报，要连同审核信息和评价信息一起删除，必须是还没有审核完的信息
        BonusScoreApplyEntity bonusScoreApplyEntity = baseMapper.selectById(id);
        if (!bonusScoreApplyEntity.getBonusStatus().eq(BonusStatusEnums.COMPLETE)) {
            baseMapper.deleteById(id);
            // 删除审核信息和评价信息
            scoreCheckService.remove(Wraps.lbQ(new ScoreCheckEntity())
                    .eq(ScoreCheckEntity::getScoreApplyId, id));
            evaluationFeedbackService.remove(Wraps.lbQ(new EvaluationFeedbackEntity())
                    .eq(EvaluationFeedbackEntity::getScoreApplyId, id));

            // 删除上传的资料
            fileOssApi.fileDelete(bonusScoreApplyEntity.getSupportMaterial());
        }
    }

    @Override
    public void updateBonusScoreApply(BonusScoreApplyUpdateDTO bonusScoreApplyUpdateDTO) {
        // 删除以前的证明材料
        fileOssApi.fileDelete(baseMapper.selectById(
                                bonusScoreApplyUpdateDTO.getId()
                        )
                        .getSupportMaterial()
        );
        BonusScoreApplyEntity bonusScoreApplyEntity = dozerUtils.map2(bonusScoreApplyUpdateDTO, BonusScoreApplyEntity.class);
        bonusScoreApplyEntity.setBonusStatus(BonusStatusEnums.HAVING);
        baseMapper.updateById(bonusScoreApplyEntity);


        // 调整已经审核的信息
        ScoreCheckEntity scoreCheck = scoreCheckService.getOne(Wraps.lbQ(new ScoreCheckEntity())
                .eq(ScoreCheckEntity::getScoreApplyId, bonusScoreApplyUpdateDTO.getId()));
        scoreCheck.setClassStatus(CheckStatusEnums.INIT)
                .setClassFeedback("")
                .setCollegeStatus(CheckStatusEnums.INIT)
                .setCollegeFeedback("")
                .setStudentOfficeStatus(CheckStatusEnums.INIT)
                .setStudentOfficeFeedback("");
        scoreCheckService.updateById(scoreCheck);

        // 通知对应的班级管理员进行审核  消息队列
        sendMasg(baseMapper.selectById(bonusScoreApplyUpdateDTO.getId()));
    }

    private void sendMasg(BonusScoreApplyEntity bonusScoreApply) {
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
    }

    /**
     * 查询当日报名的项目信息
     *
     * @param userId
     * @return
     */
    @Override
    public List<BonusScoreApplyVo> findBonusScoreSameDay(Long userId) {
        List<BonusScoreApplyEntity> bonusScoreApplyList = baseMapper.selectList(Wraps.lbQ(new BonusScoreApplyEntity())
                .eq(BonusScoreApplyEntity::getUserId, userId)
                .likeRight(BonusScoreApplyEntity::getCreateTime, LocalDate.now().toString()));

        if (CollUtil.isEmpty(bonusScoreApplyList)) {
            return Collections.emptyList();
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
                rulesMap
        );
        return bonusScoreApplyVoList;
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
        return scoreCheckService.list(Wraps.lbQ(new ScoreCheckEntity())
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

    /**
     * 封装数据
     *
     * @param bonusScoreApplyList
     * @param scoreCheckMap
     * @param evaluationFeedbackMap
     * @param moduleMap
     * @param projectMap
     * @param levelMap
     * @param rulesMap
     * @return
     */
    private List<BonusScoreApplyVo> getBonusScoreApplyVoList(List<BonusScoreApplyEntity> bonusScoreApplyList, ConcurrentMap<Long, ScoreCheckEntity> scoreCheckMap, Map<Long, EvaluationFeedbackEntity> evaluationFeedbackMap, Map<Long, CreditModuleEntity> moduleMap, Map<Long, ProjectEntity> projectMap, Map<Long, LevelEntity> levelMap, Map<Long, CreditRulesEntity> rulesMap) {
        Map<Long, EvaluationFeedbackEntity> finalEvaluationFeedbackMap = evaluationFeedbackMap;
        List<BonusScoreApplyVo> bonusScoreApplyVoList = bonusScoreApplyList.stream()
                .map(bonusScoreApply -> BonusScoreApplyVo.builder()
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
                        .scoreCheck(scoreCheckMap.getOrDefault(bonusScoreApply.getId(), null))
                        .evaluationFeedback(finalEvaluationFeedbackMap.getOrDefault(bonusScoreApply.getId(), null))
                        .build()
                )
                .collect(Collectors.toList());
        return bonusScoreApplyVoList;
    }
}