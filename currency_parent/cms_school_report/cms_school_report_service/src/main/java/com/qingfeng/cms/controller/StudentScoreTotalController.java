package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.total.service.StudentScoreTotalService;
import com.qingfeng.cms.domain.total.vo.StuModuleDataAnalysisVo;
import com.qingfeng.cms.domain.total.vo.StudentScoreDetailsVo;
import com.qingfeng.cms.domain.total.vo.StudentScoreTotalVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户总得分情况
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-15 11:50:15
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供用户总得分情况的相关功能", tags = "提供用户总得分情况的相关功能")
@RequestMapping("/student_score_total")
public class StudentScoreTotalController extends BaseController  {

    @Autowired
    private StudentScoreTotalService studentScoreTotalService;

    @ApiOperation(value = "查询学生模块得分情况", notes = "查询学生模块得分情况")
    @GetMapping("/stu/score")
    @SysLog("查询学生模块得分情况")
    public R<StudentScoreTotalVo> stuModuleScore(){
        StudentScoreTotalVo studentScoreTotalVo = studentScoreTotalService.stuModuleScore(getUserId());
        return success(studentScoreTotalVo);
    }

    @ApiOperation(value = "查询得分详情", notes = "查询得分详情")
    @GetMapping("/score/details")
    @SysLog("查询得分详情")
    public R<List<StudentScoreDetailsVo>> queryScoreDetails(){
        List<StudentScoreDetailsVo> studentScoreDetailsVoList = studentScoreTotalService.queryScoreDetails(getUserId());
        return success(studentScoreDetailsVoList);
    }

    @ApiOperation(value = "数据统计模块得分情况", notes = "数据统计模块得分情况")
    @GetMapping("/module/data/analysis")
    @SysLog("数据统计模块得分情况")
    public R<StuModuleDataAnalysisVo> moduleDataAnalysis(){
        StuModuleDataAnalysisVo stuModuleDataAnalysisVo = studentScoreTotalService.moduleDataAnalysis(getUserId());
        return success(stuModuleDataAnalysisVo);
    }
}
