package com.qingfeng.controller;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.entity.SystemFeedback;
import com.qingfeng.service.SystemFeedbackService;
import com.qingfeng.service.SystemService;
import com.qingfeng.utils.PageHelper;
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
 * @date 2022/5/12
 */
@RestController
@RequestMapping("/system")
@Api(value = "提供系统管理的接口功能",tags = "系统（反馈）管理")
@CrossOrigin
public class SystemController {

    @Autowired
    private SystemService systemService;
    @Autowired
    private SystemFeedbackService systemFeedbackService;

    @ApiOperation("根据年份获取系统学年")
    @GetMapping("/getSystemYear")
    public ResultVO getSchoolYear(String schoolYear) {
        return systemService.getSchoolYear(schoolYear);
    }

    @ApiOperation("添加课堂反馈信息")
    @PostMapping("/addFeedback/{uid}")
    public ResultVO addFeedback(@PathVariable("uid") Integer uid,@RequestBody SystemFeedback systemFeedback){
        return systemFeedbackService.addFeedback(uid,systemFeedback);
    }

    @ApiOperation("分页条件查询课堂反馈信息列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "Integer",name = "isAdmin", value = "用户身份标识", required = true),
            @ApiImplicitParam(paramType = "Integer",name = "pageNum", value = "页码", required = true),
            @ApiImplicitParam(paramType = "Integer",name = "limit", value = "每页条目数", required = true),
            @ApiImplicitParam(paramType = "Integer",name = "isReceive", value = "是否反馈（-1表示全部反馈）", required = false),
            @ApiImplicitParam(paramType = "Integer",name = "feedbackType", value = "反馈对象（0表示全部对象）", required = false),
    })
    @GetMapping("/feedbackList/{uid}")
    public ResultVO getSystemFeedback(@PathVariable("uid") Integer uid, Integer isAdmin,Integer pageNum,Integer limit,Integer isReceive,Integer feedbackType){
        try {
            PageHelper<SystemFeedback> feedbackList = systemFeedbackService.queryFeedbacks(uid,isAdmin,pageNum,limit,isReceive,feedbackType);
            return new ResultVO(ResStatus.OK,"suuccess",feedbackList);
        } catch (Exception e) {
            return new ResultVO(ResStatus.NO,"系统异常！",null);
        }
    }

    @ApiOperation("删除反馈信息")
    @PostMapping("/deleteFeedback")
    public ResultVO deleteFeedback(@RequestBody Integer[] feedbacks){
        return systemFeedbackService.deleteFeedback(feedbacks);
    }
}
