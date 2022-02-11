package com.qingfeng.controller;

import com.qingfeng.service.UserService;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 用户持久层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/10
 */
@RestController
@RequestMapping("/user")
@Api(value = "提供用户的登录和添加的接口",tags = "用户管理")
public class UserController {

    @Autowired
    private UserService userService;

    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "string",name = "username",value = "用户登录账号",required = true),
            @ApiImplicitParam(paramType = "string",name = "password",value = "用户登录密码",required = true),
            @ApiImplicitParam(paramType = "string",name = "code",value = "用户输入的验证码",required = true),
            @ApiImplicitParam(paramType = "string",name = "oldCode",value = "系统生成的验证码",required = true)
    })
    @PostMapping("/login")
    public ResultVO login(@RequestParam(value = "username") String username,
                          @RequestParam(value = "password") String password,
                          @RequestParam(value = "code") String code,
                          @RequestParam(value = "oldCode") String oldCode){
        //校验验证码
        if (oldCode.equalsIgnoreCase(code)){
            //说明验证码通过
            return userService.checkLogin(username, password);
        }else{
            return new ResultVO(ResStatus.NO,"验证码不正确",null);
        }
    }

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
}
