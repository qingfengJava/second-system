package com.qingfeng.controller;

import com.qingfeng.entity.Users;
import com.qingfeng.service.UserService;
import com.qingfeng.utils.FileUtils;
import com.qingfeng.vo.ResStatus;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

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
@CrossOrigin
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * 文件路径
     */
    @Value("${photo.file.dir}")
    private String realPath;

    @ApiOperation("用户登录接口")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType = "string",name = "username",value = "用户登录账号",required = true),
            @ApiImplicitParam(paramType = "string",name = "password",value = "用户登录密码",required = true),
    })
    @GetMapping("/login")
    public ResultVO login(String username,
                          String password){
        return userService.checkLogin(username, password);
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

    @ApiOperation("用户修改密码接口")
    @ApiImplicitParam(paramType = "string",name = "password",value = "新密码",required = true)
    @GetMapping("/update/{uid}")
    public ResultVO updatePassword(@PathVariable("uid") String uid,String password){
        return userService.updatePassword(uid,password);
    }

    @ApiOperation("完善用户信息接口")
    @PostMapping("/updateMessage/{uid}")
    public ResultVO updateMsg(@PathVariable("uid") String uid,@RequestBody Users users,String photo, MultipartFile img){
        try {
            //判断是否更新头像  空是true，表示没有更新头像
            boolean notEempty = !img.isEmpty();
            //不为空
            if (notEempty){
                //检查就照片是否存在，存在就将其删除掉，再保存新照片
                File file = new File(realPath,photo);
                if (file.exists()) {
                    //如果文件存在，就删除文件
                    if (!photo.equals("default.png")){
                        //如果不是默认头像就删除老照片
                        file.delete();
                    }
                }
                //处理新的头像上传   1、处理头像的上传 & 修改文件名
                String newFileName = FileUtils.uploadFile(img, realPath);
                //修改用户头像的信息
                users.setPhoto(newFileName);
            }else {
                //否则不修改头像
                users.setPhoto(photo);
            }
            //调用service层完善用户信息的方法
            return userService.updateMessage(uid,users);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultVO(ResStatus.NO,"完善信息出现未知的异常！",null);
        }
    }
}
