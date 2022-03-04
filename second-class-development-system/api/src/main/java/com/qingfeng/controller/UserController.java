package com.qingfeng.controller;

import com.qingfeng.entity.UserInfo;
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
 * 用户基础控制层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/10
 */
@RestController
@RequestMapping("/user")
@Api(value = "提供用户管理模块的功能",tags = "用户管理层接口")
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

    @ApiOperation("用户修改密码接口")
    @ApiImplicitParam(paramType = "string",name = "password",value = "新密码",required = true)
    @GetMapping("/update/{uid}")
    public ResultVO updatePassword(@PathVariable("uid") String uid,String password){
        return userService.updatePassword(uid,password);
    }

    @ApiOperation("完善用户基本信息接口")
    @PostMapping("/updateMessage/{uid}")
    public ResultVO updateMsg(@PathVariable("uid") String uid,Users user,String oldPhoto, MultipartFile img){
        try {
            //判断是否更新头像  空是true，表示没有更新头像
            boolean notEempty = (img != null);
            //不为空
            if (notEempty){
                //检查就照片是否存在，存在就将其删除掉，再保存新照片
                File file = new File(realPath,oldPhoto);
                if (file.exists()) {
                    //如果文件存在，就删除文件
                    if (!oldPhoto.equals("default.png")){
                        //如果不是默认头像就删除老照片
                        file.delete();
                    }
                }
                //处理新的头像上传   1、处理头像的上传 & 修改文件名
                String newFileName = FileUtils.uploadFile(img, realPath);
                //修改用户头像的信息
                user.setPhoto(newFileName);
            }else {
                //否则不修改头像
                user.setPhoto(oldPhoto);
            }
            //调用service层完善用户信息的方法
            return userService.updateMessage(uid,user);
        } catch (IOException e) {
            e.printStackTrace();
            return new ResultVO(ResStatus.NO,"完善信息出现未知的异常！",null);
        }
    }

    @ApiOperation("查询学生用户基本信息接口")
    @PostMapping("/checkUser/{uid}")
    public ResultVO checkUser(@PathVariable("uid") String uid){
        return userService.checkUser(uid);
    }

    @ApiOperation("查询用户详情信息接口")
    @PostMapping("/checkUserInfo/{uid}")
    public ResultVO checkUserInfo(@PathVariable("uid") String uid,Integer isAdmin){
        //查询用户详情，因为不同的角色对应的详情表不一样，因此要根据isAdmin做用户身份判断
        return userService.checkUserInfo(uid,isAdmin);
    }

    @ApiOperation("添加或修改学生用户详情接口")
    @PostMapping("/updateUserInfo/{uid}")
    public ResultVO updateUserInfo(@PathVariable("uid") Integer uid, UserInfo userInfo){
        //添加或保存用户详情
        return userService.updateUserInfo(uid,userInfo);
    }
}
