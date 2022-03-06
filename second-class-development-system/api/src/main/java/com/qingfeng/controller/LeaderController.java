package com.qingfeng.controller;

import com.qingfeng.entity.TeacherInfo;
import com.qingfeng.service.UserService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 学校领导及管理员的控制层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/3
 */
@RestController
@Api(value = "提供学校领导及管理员的管理模块的功能",tags = "校领导管理层接口")
@CrossOrigin
@RequestMapping("/leader")
public class LeaderController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户添加接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "string",name = "username",value = "用户名/账号",required = true),
            @ApiImplicitParam(paramType = "string",name = "password",value = "密码",required = true),
            @ApiImplicitParam(paramType = "int",name = "isAdmin",value = "用户类型",required = true)
    })
    @PostMapping("/add")
    public ResultVO userAdd(String username, String password, int isAdmin){
        return userService.userAdd(username,password,isAdmin);
    }

    @ApiOperation("添加或修改校领导用户详情接口")
    @PostMapping("/updateTeacherInfo/{uid}")
    public ResultVO updateTeacherInfo(@PathVariable("uid") Integer uid, @RequestBody TeacherInfo teacherInfo){
        return userService.updateTeacherInfo(uid, teacherInfo);
    }
}
