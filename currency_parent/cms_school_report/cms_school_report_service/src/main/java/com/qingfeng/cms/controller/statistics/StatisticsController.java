package com.qingfeng.cms.controller.statistics;

import com.qingfeng.cms.biz.statistics.service.StatisticsService;
import com.qingfeng.cms.domain.statistics.vo.ClassModuleVo;
import com.qingfeng.cms.domain.statistics.vo.ClazzCreditsVo;
import com.qingfeng.cms.domain.statistics.vo.StuSemesterCreditsVo;
import com.qingfeng.cms.domain.total.vo.StuModuleDataAnalysisVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import com.qingfeng.sdk.auth.role.UserRoleApi;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
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
    @Autowired
    private UserRoleApi userRoleApi;

    @ApiOperation(value = "学生学期学分修读情况", notes = "学生学期学分修读情况")
    @GetMapping("/stu")
    @SysLog("学生学期学分修读情况")
    public R<StuSemesterCreditsVo> stuSemesterCredits() {
        StuSemesterCreditsVo stuSemesterCreditsVoList = statisticsService.stuSemesterCredits(getUserId());
        return success(stuSemesterCreditsVoList);
    }

    @ApiOperation(value = "首页返回用户角色code", notes = "首页返回用户角色code")
    @GetMapping("/user/code")
    @SysLog("首页返回用户角色code")
    public R<String> getRole() {
        return success(
                userRoleApi.findRoleIdByUserId(getUserId())
                        .getData()
                        .getCode()
        );
    }

    @ApiOperation(value = "班级的方案模块下的学生参与人数", notes = "班级的方案模块下的学生参与人数")
    @GetMapping("/clazz/module")
    @SysLog("班级的方案模块下的学生参与人数")
    public R<ClassModuleVo> classModule(){
        ClassModuleVo classModuleVo = statisticsService.classModule(getUserId());
        return success(classModuleVo);
    }

    @ApiOperation(value = "班级的学生学分修读情况", notes = "班级的学生学分修读情况")
    @GetMapping("/clazz/credits")
    @SysLog("班级的学生学分修读情况")
    public R<List<ClazzCreditsVo>> clazzCredits(){
        List<ClazzCreditsVo> clazzCredits = statisticsService.clazzCredits();
        return success(clazzCredits);
    }

    @ApiOperation(value = "根据学生Id，查询学生第二课堂参与情况", notes = "根据学生Id，查询学生第二课堂参与情况")
    @GetMapping("/clazz/stuId/{stuId}")
    @SysLog("根据学生Id，查询学生第二课堂参与情况")
    public R<StuModuleDataAnalysisVo> clazzStuId(@PathVariable("stuId") Long stuId){
        StuModuleDataAnalysisVo studentScoreTotalVo = statisticsService.clazzStuId(stuId);
        return success(studentScoreTotalVo);
    }

    @ApiOperation(value = "各年级学分修读情况", notes = "各年级学分修读情况")
    @GetMapping("/grade/score")
    @SysLog("各年级学分修读情况")
    public R gradeScore(){

        return success();
    }

    @ApiOperation(value = "各年级各模块参与人数", notes = "各年级各模块参与人数")
    @GetMapping("/grade/module")
    @SysLog("各年级各模块参与人数")
    public R gradeModule(){

        return success();
    }
}
