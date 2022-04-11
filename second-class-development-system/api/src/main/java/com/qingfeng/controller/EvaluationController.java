package com.qingfeng.controller;

import com.qingfeng.service.EvaluationService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 活动评价管理控制层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/4/11
 */
@RestController
@Api(value = "提供活动评价管理的相关接口", tags = "活动评价管理")
@RequestMapping("/evaluation")
@CrossOrigin
public class EvaluationController {

    @Autowired
    private EvaluationService evaluationService;

    @ApiOperation("根据用户Id和活动Id查询评价")
    @GetMapping("/findByUid/{uid}/{applyId}")
    public ResultVO selectEvaluationOne(@PathVariable("uid") Integer uid, @PathVariable("applyId") Integer applyId) {
        return evaluationService.selectEvaluationByUid(uid, applyId);
    }
}
