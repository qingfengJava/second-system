package com.qingfeng.controller;

import com.qingfeng.entity.Apply;
import com.qingfeng.entity.AuditForm;
import com.qingfeng.entity.Check;
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
@RequestMapping("/activeApply")
@Api(value = "提供活动申请相关的接口功能",tags = "活动申请")
@CrossOrigin
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    @ApiOperation("活动申请接口")
    @PostMapping("/apply/{uid}")
    public ResultVO applyActive(@PathVariable("uid") Integer uid, @RequestBody Apply apply){
        //如果活动申请成功，要给相关的负责人发送邮件以提醒审核
        return applyService.applyActive(uid, apply);
    }

    @ApiOperation("活动申请审查")
    @PostMapping("/check/{applyId}")
    public ResultVO checkApplyActive(@PathVariable("applyId") Integer applyId,@RequestBody AuditForm auditForm){
        //根据活动申请Id进行活动名审核
        return applyService.checkApplyActive(applyId,auditForm);
    }

    @ApiOperation("活动申请删除")
    @DeleteMapping("/delete/{applyId}")
    public ResultVO deleteApplyActive(@PathVariable("applyId") Integer applyId){
        //根据活动申请表的Id删除要申请的活动
        return applyService.deleteApplyActive(applyId);
    }

    @ApiOperation("活动申请信息修改")
    @PostMapping("/update/{applyId}")
    public ResultVO updateApplyActive(@PathVariable("applyId") Integer applyId,@RequestBody Apply apply){
        //根据社团申请活动的主键Id修改活动申请的信息
        return applyService.updateApplyActive(applyId, apply);
    }

    @ApiOperation("查询社团学年活动个数")
    @PostMapping("/queryActive/{userId}")
    public ResultVO queryActiveYearCount(@PathVariable("userId") Integer userId){
        return applyService.queryActiveYearCount(userId);
    }

    @ApiOperation("社团联进行活动最终审核")
    @PostMapping("/finalCheck")
    public ResultVO finalCheck(@RequestBody Check check){
        return applyService.finalCheck(check);
    }

    @ApiOperation("查询社团年度失败的活动个数")
    @PostMapping("/queryActiveFail/{userId}")
    public ResultVO queryActiveFailCount(@PathVariable("userId") Integer userId){
        return applyService.queryActiveFailCount(userId);
    }
}
