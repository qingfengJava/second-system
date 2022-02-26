package com.qingfeng.controller;

import com.qingfeng.service.ActiveService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/14
 */
@RestController
@RequestMapping("/active")
@Api(value = "提供活动相关的接口功能",tags = "活动管理")
@CrossOrigin
public class ActiveController {

    @Autowired
    private ActiveService activeService;


    @ApiOperation("学生活动报名列表查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "int",name = "pageNum",value = "页码",required = true),
            @ApiImplicitParam(paramType = "int",name = "limit",value = "每页条数",required = true)
    })
    @PostMapping("/checkRegistration/{uid}")
    public ResultVO checkRegistration(@PathVariable("uid") String uid,int pageNum,int limit){
        //调用业务层接口，查询用户报名待参与的活动列表
        return activeService.checkRegistration(uid,pageNum,limit);
    }

    @ApiOperation("新活动查询接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "int",name = "pageNum",value = "页码",required = true),
            @ApiImplicitParam(paramType = "int",name = "limit",value = "每页条数",required = true)
    })
    @PostMapping("/queryApply")
    public ResultVO queryApply(int pageNum,int limit){
        return activeService.queryApply(pageNum,limit);
    }

    @ApiOperation("活动详情查询接口")
    @PostMapping("/queryActive/{applyId}")
    public ResultVO queryApplyById(@PathVariable("applyId") String applyId){
        return activeService.queryApplyDetails(applyId);
    }
}
