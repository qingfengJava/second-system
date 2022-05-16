package com.qingfeng.controller;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.entity.Notice;
import com.qingfeng.service.NoticeService;
import com.qingfeng.utils.PageHelper;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 公告持久层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/4
 */
@RestController
@RequestMapping("/notice")
@Api(value = "提供公告相关模块的功能",tags = "公告层接口")
@CrossOrigin
public class NoticeController {

    @Autowired
    private NoticeService noticeService;

    @ApiOperation("发布公告")
    @PostMapping("/add/{uid}")
    public ResultVO addNotice(@PathVariable("uid") Integer userId,@RequestBody Notice notice){
        return noticeService.addNotice(userId,notice);
    }

    @ApiOperation("修改公告信息")
    @PostMapping("/update/{noticeId}")
    public ResultVO updateNotice(@PathVariable("noticeId") Integer noticeId,@RequestBody Notice notice){
        //根据主键Id对公告内容进行修改
        return noticeService.updateNotice(noticeId,notice);
    }

    @ApiOperation("查询公告详情信息")
    @PostMapping("/queryDetails/{noticeId}")
    public ResultVO queryNoticeDetails(@PathVariable("noticeId") Integer noticeId){
        //根据公告表主键Id查询对应公告详情信息
        return noticeService.queryDetails(noticeId);
    }

    @ApiOperation("分页条件查询公告列表（首页显示用）")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "int",name = "pageNum",value = "页码",required = true),
            @ApiImplicitParam(paramType = "int",name = "limit",value = "每页条数",required = true)
    })
    @PostMapping("/query/{isAdmin}")
    public ResultVO queryNotice(@PathVariable("isAdmin") Integer isAdmin, int pageNum,int limit){
        //分页查询公告列表
        return noticeService.queryNotice(isAdmin,pageNum,limit);
    }

    @ApiOperation("根据用户Id分页条件查询公告列表")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "Integer",name = "pageNum",value = "页码",required = true),
            @ApiImplicitParam(paramType = "Integer",name = "limit",value = "每页条数",required = true),
            @ApiImplicitParam(paramType = "title",name = "title",value = "公告主题",required = false),
            @ApiImplicitParam(paramType = "Integer",name = "status",value = "公告状态",required = false)
    })
    @GetMapping("/queryNotice/{uid}")
    public ResultVO queryNoticeByUserId(@PathVariable("uid") Integer userId, Integer pageNum,Integer limit,String title,Integer status){
        //分页查询公告列表
        PageHelper<Notice> pageHelper = noticeService.queryNoticeListByUserId(userId,pageNum,limit,title,status);
        return new ResultVO(ResStatus.OK,"success",pageHelper);
    }
}
