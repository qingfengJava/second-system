package com.qingfeng.controller;

import com.qingfeng.service.DataAnalysisService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * 数据分析持久层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/4/12
 */
@RestController
@Api(value = "提供数据分析管理的相关接口", tags = "数据分析管理")
@RequestMapping("/analysis")
@CrossOrigin
public class DataAnalysisController {

    @Autowired
    private DataAnalysisService dataAnalysisService;

    @ApiOperation("查询学生参与活动的数量")
    @GetMapping("/num/{uid}")
    public ResultVO queryActiveNum(@PathVariable("uid") Integer uid) {
        //根据用户Id查询学生参与活动的数量
        return dataAnalysisService.queryActiveNum(uid);
    }

    @ApiOperation("查询学生参与个活动类型的数量")
    @GetMapping("/typenum/{uid}")
    public ResultVO queryTypeActiveNum(@PathVariable("uid") Integer uid){
        //根据用户Id查询学生参与各活动类型的数量
        return dataAnalysisService.queryTypeActiveNum(uid);
    }

    @ApiOperation("查询学生大一到大四各个阶段修的学分")
    @GetMapping("/score/{uid}")
    public ResultVO queryScore(@PathVariable("uid") Integer uid){
        //根据用户Id查询学生大一到大四各个阶段修的学分
        return dataAnalysisService.queryScore(uid);
    }

}
