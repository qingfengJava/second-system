package com.qingfeng.controller;

import com.qingfeng.entity.Apply;
import com.qingfeng.service.ApplyService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 活动申请控制层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/26
 */
@RestController
@RequestMapping("/active-apply")
@Api(value = "提供活动申请相关的接口功能",tags = "活动申请")
@CrossOrigin
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    @ApiOperation("活动申请接口")
    @PostMapping("/apply")
    public ResultVO applyActive(@RequestBody Apply apply){
        //将用户Id存入apply对象中
        return applyService.applyActive(apply);
    }

    @ApiOperation("活动申请审核")
    @PostMapping("/check/{applyId}")
    public ResultVO checkApplyActive(@PathVariable("applyId") Integer applyId,Integer isAgree){
        //根据活动申请Id进行活动名审核
        return applyService.checkApplyActive(applyId,isAgree);
    }

    @ApiOperation("活动申请删除")
    @DeleteMapping("/delete/{applyId}")
    public ResultVO deleteApplyActive(@PathVariable("applyId") Integer applyId){
        //根据活动申请表的Id删除要申请的活动
        return applyService.deleteApplyActive(applyId);
    }
}
