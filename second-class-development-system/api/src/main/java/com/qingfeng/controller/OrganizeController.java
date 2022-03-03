package com.qingfeng.controller;

import com.qingfeng.entity.Notice;
import com.qingfeng.service.NoticeService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 社团组织控制层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/3
 */
@RestController
@RequestMapping("/organize")
@Api(value = "提供社团组织管理模块的功能",tags = "社团组织管理层接口")
@CrossOrigin
public class OrganizeController {

    @Autowired
    private NoticeService noticeService;

    @ApiOperation("发布公告")
    @PostMapping("/add/{uid}")
    public ResultVO addNotice(@PathVariable("uid") Integer userId, Notice notice){
        return noticeService.addNotice(userId,notice);
    }

    @ApiOperation("修改公告信息")
    public ResultVO updateNotice(){
        return null;
    }
}
