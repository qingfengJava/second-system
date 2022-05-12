package com.qingfeng.controller;

import com.qingfeng.service.UserNoticeService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/11
 */
@RestController
@RequestMapping("/user/notioce")
@Api(value = "提供用户公告管理模块的功能",tags = "用户公告管理层接口")
@CrossOrigin
public class UserNoticeController {

    @Autowired
    private UserNoticeService userNoticeService;

    @ApiOperation("添加用户公告查看信息")
    @PostMapping("/add/{uid}/{noticeId}")
    public ResultVO addUserNotice(@PathVariable("uid") Integer uid, @PathVariable("noticeId") Integer noticeId){
        return userNoticeService.addUserNotice(uid, noticeId);
    }
}
