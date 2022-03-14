package com.qingfeng.controller;

import com.qingfeng.entity.Grade;
import com.qingfeng.service.GradeService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 社团评级持久层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/13
 */
@RestController
@RequestMapping("/grade")
@CrossOrigin
@Api(value = "社团评级相关的接口功能",tags = "社团评级管理")
public class GradeController {

    @Autowired
    private GradeService gradeService;

    @ApiOperation("社团评级")
    @PostMapping("/comment")
    public ResultVO commentGrade( @RequestBody Grade grade){
        return gradeService.commentGrade(grade);
    }
}
