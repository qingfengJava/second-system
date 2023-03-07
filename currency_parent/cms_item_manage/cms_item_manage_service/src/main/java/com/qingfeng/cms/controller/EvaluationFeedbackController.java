package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.evaluation.service.EvaluationFeedbackService;
import com.qingfeng.cms.domain.evaluation.entity.EvaluationFeedbackEntity;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;



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
@Api(value = "提供的相关功能", tags = "")
@RequestMapping("/evaluation_feedback")
public class EvaluationFeedbackController extends BaseController  {

    @Autowired
    private EvaluationFeedbackService evaluationFeedbackService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

        return success();
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		EvaluationFeedbackEntity evaluationFeedback = evaluationFeedbackService.getById(id);

        return success();
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody EvaluationFeedbackEntity evaluationFeedback){
		evaluationFeedbackService.save(evaluationFeedback);

        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody EvaluationFeedbackEntity evaluationFeedback){
		evaluationFeedbackService.updateById(evaluationFeedback);

        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		evaluationFeedbackService.removeByIds(Arrays.asList(ids));

        return success();
    }

}
