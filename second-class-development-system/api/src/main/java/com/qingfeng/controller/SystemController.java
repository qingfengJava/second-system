package com.qingfeng.controller;

import com.qingfeng.service.SystemService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/12
 */
@RestController
@RequestMapping("/system")
@Api(value = "提供系统管理的接口功能",tags = "系统管理")
@CrossOrigin
public class SystemController {

    @Autowired
    private SystemService systemService;

    @ApiOperation("根据年份获取系统学年")
    @GetMapping("/getSystemYear")
    public ResultVO getSchoolYear(String schoolYear) {
        return systemService.getSchoolYear(schoolYear);
    }
}
