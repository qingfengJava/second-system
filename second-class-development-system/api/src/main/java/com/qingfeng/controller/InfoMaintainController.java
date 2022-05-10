package com.qingfeng.controller;

import com.qingfeng.entity.InfoMaintain;
import com.qingfeng.service.InfoMaintainService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/10
 */
@RestController
@RequestMapping("/info/maintain")
@CrossOrigin
@Api(value = "用户基本信息管理接口功能",tags = "用户基本信息管理")
public class InfoMaintainController {

    @Autowired
    private InfoMaintainService infoMaintainService;

    @ApiOperation("查询是否开启学生信息维护功能")
    @GetMapping("/stu/getInfo/maintain")
    public ResultVO getUserInfoMaintain(){
        return infoMaintainService.getUserInfoMaintain();
    }

    @ApiOperation("添加信息维护功能")
    @PostMapping("/stu/addInfo/maintain")
    public ResultVO addUserInfoMaintain(@RequestBody InfoMaintain infoMaintain){
        return infoMaintainService.addUserInfoMaintain(infoMaintain);
    }

    @ApiOperation("关闭信息维护功能")
    @PutMapping("/stu/updateInfo/maintain/{id}/{type}")
    public ResultVO updateUserInfoMaintain(@PathVariable("id") Integer id,@PathVariable("type") Integer type){
        return infoMaintainService.updateUserInfoMaintain(id,type);
    }

    @ApiOperation("查询是否开启教师信息维护功能")
    @GetMapping("/teach/getInfo/maintain")
    public ResultVO getTeacherInfoMaintain(){
        return infoMaintainService.getTeacherInfoMaintain();
    }
}
