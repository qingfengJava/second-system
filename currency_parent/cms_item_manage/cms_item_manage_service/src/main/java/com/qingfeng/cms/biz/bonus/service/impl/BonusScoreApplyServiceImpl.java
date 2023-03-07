package com.qingfeng.cms.biz.bonus.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.bonus.dao.BonusScoreApplyDao;
import com.qingfeng.cms.biz.bonus.service.BonusScoreApplyService;
import com.qingfeng.cms.biz.check.service.ScoreCheckService;
import com.qingfeng.cms.biz.evaluation.service.EvaluationFeedbackService;
import com.qingfeng.cms.domain.bonus.dto.BonusScoreApplySaveDTO;
import com.qingfeng.cms.domain.bonus.dto.BonusScoreApplyUpdateDTO;
import com.qingfeng.cms.domain.bonus.entity.BonusScoreApplyEntity;
import com.qingfeng.cms.domain.bonus.enums.BonusStatusEnums;
import com.qingfeng.cms.domain.bonus.vo.BonusScoreApplyVo;
import com.qingfeng.cms.domain.check.entity.ScoreCheckEntity;
import com.qingfeng.cms.domain.check.enums.CheckStatusEnums;
import com.qingfeng.cms.domain.evaluation.entity.EvaluationFeedbackEntity;
import com.qingfeng.cms.domain.project.dto.ProjectQueryDTO;
import com.qingfeng.cms.domain.project.enums.ProjectCheckEnum;
import com.qingfeng.cms.domain.project.vo.ProjectListVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.sdk.planstandard.project.ProjectApi;
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

    /**
     * 根据模块Id查询项目等级学分信息
     *
     * @param moduleId
     * @param userId
     * @return
     */
    @Override
    public List<ProjectListVo> projectList(Long moduleId, Long userId) {
        List<ProjectListVo> projectVoList = projectApi.list(
                ProjectQueryDTO.builder()
                        .moduleId(moduleId)
                        .isCheck(ProjectCheckEnum.IS_FINISHED)
                        .isEnable(1)
                        .build()
        ).getData();

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
        baseMapper.insert(bonusScoreApply);

        // 保存一条初始的审核记录
        ScoreCheckEntity scoreCheck = ScoreCheckEntity.builder()
                .classStatus(CheckStatusEnums.INIT)
                .collegeStatus(CheckStatusEnums.INIT)
                .studentOfficeStatus(CheckStatusEnums.INIT)
                .build();
        scoreCheck.setScoreApplyId(bonusScoreApply.getId());
        scoreCheckService.save(scoreCheck);

        // TODO 需要通知班级管理员进行审核
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
        }
    }

    @Override
    public void updateBonusScoreApply(BonusScoreApplyUpdateDTO bonusScoreApplyUpdateDTO) {
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

        // TODO 通知对应的班级管理员进行审核
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
                .eq(BonusScoreApplyEntity::getCreateTime, LocalDate.now()));

        if (CollUtil.isEmpty(bonusScoreApplyList)) {
            return Collections.emptyList();
        }

        List<BonusScoreApplyVo> bonusScoreApplyVoList = dozerUtils.mapList(bonusScoreApplyList, BonusScoreApplyVo.class);
        // 查询审核和评价信息
        List<Long> scoreApplyIds = bonusScoreApplyVoList.stream()
                .map(BonusScoreApplyVo::getId)
                .collect(Collectors.toList());

        ConcurrentMap<Long, ScoreCheckEntity> scoreCheckMap = scoreCheckService.list(Wraps.lbQ(new ScoreCheckEntity())
                        .in(ScoreCheckEntity::getScoreApplyId, scoreApplyIds))
                .stream()
                .collect(Collectors.toConcurrentMap(
                                ScoreCheckEntity::getScoreApplyId,
                                Function.identity()
                        )
                );

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

        // 封装数据
        Map<Long, EvaluationFeedbackEntity> finalEvaluationFeedbackMap = evaluationFeedbackMap;
        bonusScoreApplyVoList.forEach(bonusScoreApplyVo -> {
            bonusScoreApplyVo.setScoreCheck(scoreCheckMap.getOrDefault(bonusScoreApplyVo.getId(), null));
            bonusScoreApplyVo.setEvaluationFeedback(finalEvaluationFeedbackMap.getOrDefault(bonusScoreApplyVo.getId(), null));
        });

        return bonusScoreApplyVoList;
    }
}