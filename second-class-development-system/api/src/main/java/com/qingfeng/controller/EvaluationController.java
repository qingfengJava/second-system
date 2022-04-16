package com.qingfeng.controller;

import com.qingfeng.entity.Evaluation;
import com.qingfeng.service.EvaluationService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @ApiOperation("根据用户Id和活动Id查询主评价信息")
    @GetMapping("/findByUid/{uid}/{applyId}")
    public ResultVO selectEvaluationOne(@PathVariable("uid") Integer uid, @PathVariable("applyId") Integer applyId) {
        return evaluationService.selectEvaluationByUid(uid, applyId);
    }

    @ApiOperation("添加活动主评价")
    @PostMapping("/add/{uid}/{applyId}")
    public ResultVO addEvaluation(@PathVariable("uid") Integer uid, @PathVariable("applyId") Integer applyId, @RequestBody Evaluation evaluation) {
        //添加活动评价的同时，需要给用户加分
        return evaluationService.addEvaluation(uid, applyId, evaluation);
    }

    @ApiOperation("添加活动评论的子评论")
    @PostMapping("/addChild/{uid}/{applyId}/{parentId}")
    public ResultVO addChildEvaluation(@PathVariable("uid") Integer uid, @PathVariable("applyId") Integer applyId, @PathVariable("parentId") Integer parentId, @RequestBody Evaluation evaluation) {
        return evaluationService.addChildEvaluation(uid, applyId, parentId, evaluation);
    }

    @ApiOperation("分页查询活动主评论")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "int",name = "pageNum",value = "页码",required = true),
            @ApiImplicitParam(paramType = "int",name = "limit",value = "每页条数",required = true)
    })
    @GetMapping("/findByApplyId/{applyId}")
    public ResultVO selectEvaluationByApplyId(@PathVariable("applyId") Integer applyId,
                                              int pageNum,
                                              int limit) {
        return evaluationService.selectEvaluationByApplyId(applyId, pageNum, limit);
    }

}
