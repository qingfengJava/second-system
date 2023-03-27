package com.qingfeng.cms.controller.statistics;

import com.qingfeng.cms.biz.statistics.service.StatisticsService;
import com.qingfeng.cms.domain.statistics.vo.StuSemesterCreditsVo;
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
 * 学生：学期学分修读情况、（模块得分情况、模块项目个数占比）
 * 班级：学生学分修读情况占比、班级活动占比情况（每个模块下的学生参与人数）
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/27
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供数据分析模块的相关功能", tags = "提供数据分析模块的相关功能")
@RequestMapping("/statistics")
public class StatisticsController extends BaseController {

    @Autowired
    private StatisticsService statisticsService;

    @ApiOperation(value = "学生学期学分修读情况", notes = "学生学期学分修读情况")
    @GetMapping("/stu")
    @SysLog("学生学期学分修读情况")
    public R<List<StuSemesterCreditsVo>> stuSemesterCredits(){
        List<StuSemesterCreditsVo> stuSemesterCreditsVoList = statisticsService.stuSemesterCredits(getUserId());
        return success(stuSemesterCreditsVoList);
    }
}
