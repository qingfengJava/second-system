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

    /**
     * 活动申请信息查询，做数据回显使用
     * @param applyId
     * @return
     */
    @ApiOperation("活动申请信息查询接口")
    @PostMapping("/queryActive/{applyId}")
    public ResultVO queryApplyById(@PathVariable("applyId") String applyId){
        return activeService.queryApplyById(applyId);
    }

    /**
     * 根据申请Id查询活动申请的详细信息
     * @param applyId
     * @return
     */
    @ApiOperation("活动申请详情信息查询接口")
    @PostMapping("/queryActiveDetails/{applyId}")
    public ResultVO queryApplyDetails(@PathVariable("applyId") Integer applyId){
        return activeService.queryApplyDetails(applyId);
    }

    /**
     * 报名活动详情查询：
     *      既要查询用户报名表的信息，也要查询对应的活动申请表的信息
     * @param applyId
     * @return
     */
    @ApiOperation("报名活动详情接口查看")
    @PostMapping("/queryApplyActiveDetails/{uid}/{applyId}")
    public ResultVO queryRegistActiveDetails(@PathVariable("uid") Integer uid,@PathVariable("applyId") Integer applyId){
        return activeService.queryRegistActiveDetails(uid,applyId);
    }

    /**
     * 查询某个具体活动的报名总人数Id
     * @param applyId
     * @return
     */
    @ApiOperation("具体活动报名总人数查询")
    @PostMapping("/queryRegistCount/{applyId}")
    public ResultVO queryRegistCount(@PathVariable("applyId") Integer applyId){
        return activeService.queryRegistCount(applyId);
    }

    @ApiOperation("查询社团学年活动个数")
    @PostMapping("/queryActiveYear/{userId}")
    public ResultVO queryActiveYearCount(@PathVariable("userId") Integer userId){
        return activeService.queryActiveYearCount(userId);
    }
}
