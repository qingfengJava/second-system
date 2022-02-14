package com.qingfeng.controller;

import com.qingfeng.entity.Apply;
import com.qingfeng.service.ActiveService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/14
 */
@RestController
@RequestMapping("/active")
@Api(value = "提供活动相关的接口功能",tags = "活动管理")
@CrossOrigin
public class ActiveController {

    @Autowired
    private ActiveService activeService;

    @ApiOperation("活动申请接口")
    @PostMapping("/apply")
    public ResultVO applyActive(@RequestBody Apply apply){
        System.out.println(apply);
        //将用户Id存入apply对象中
        return activeService.applyActive(apply);
    }
}
