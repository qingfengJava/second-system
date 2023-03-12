package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.evaluation.service.EvaluationFeedbackService;
import com.qingfeng.cms.domain.evaluation.dto.EvaluationFeedbackSaveDTO;
import com.qingfeng.cms.domain.evaluation.entity.EvaluationFeedbackEntity;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * 项目评价在线反馈表(对已参与的项目进行评价反馈)（类似于间接评价）
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供项目在线评价反馈的相关功能", tags = "提供项目在线评价反馈的相关功能")
@RequestMapping("/evaluation_feedback")
public class EvaluationFeedbackController extends BaseController {

    @Autowired
    private EvaluationFeedbackService evaluationFeedbackService;
    @Autowired
    private DozerUtils dozerUtils;

    @ApiOperation(value = "项目评价信息保存", notes = "项目评价信息保存")
    @PostMapping
    @SysLog("项目评价信息保存")
    public R saveEvaluation(@RequestBody @Validated EvaluationFeedbackSaveDTO evaluationFeedbackDTO) {
        EvaluationFeedbackEntity feedbackEntity = dozerUtils.map2(
                evaluationFeedbackDTO,
                EvaluationFeedbackEntity.class);
        feedbackEntity.setUserId(getUserId());
        evaluationFeedbackService.save(feedbackEntity);
        return success();
    }

}
