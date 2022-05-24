package com.qingfeng.controller;

import com.qingfeng.service.ScoreService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/22
 */
@RestController
@RequestMapping("/score")
@Api(value = "提供成绩管理相关的接口功能",tags = "成绩管理")
@CrossOrigin
public class ScoreController {

    @Autowired
    private ScoreService scoreService;

    @ApiOperation("学生成绩查询")
    @GetMapping("/queryScore/{uid}")
    public ResultVO queryScore(@PathVariable("uid") Integer uid){
        return scoreService.queryScore(uid);
    }
}
