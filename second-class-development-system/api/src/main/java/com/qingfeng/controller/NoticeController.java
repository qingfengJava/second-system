package com.qingfeng.controller;

import com.qingfeng.entity.Notice;
import com.qingfeng.service.NoticeService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
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
    public ResultVO addNotice(@PathVariable("uid") Integer userId, Notice notice){
        return noticeService.addNotice(userId,notice);
    }

    @ApiOperation("修改公告信息")
    @PostMapping("/update/{noticeId}")
    public ResultVO updateNotice(@PathVariable("noticeId") Integer noticeId, Notice notice){
        //根据主键Id对公告内容进行修改
        return noticeService.updateNotice(noticeId,notice);
    }

    @ApiOperation("查询公告详情信息")
    @PostMapping("/queryDetails/{noticeId}")
    public ResultVO queryNoticeDetails(@PathVariable("noticeId") Integer noticeId){
        //根据公告表主键Id查询对应公告详情信息
        return noticeService.queryDetails(noticeId);
    }
}
