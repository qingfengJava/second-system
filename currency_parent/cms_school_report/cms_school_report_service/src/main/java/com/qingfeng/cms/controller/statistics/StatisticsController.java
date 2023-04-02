package com.qingfeng.cms.controller.statistics;

import com.qingfeng.cms.biz.statistics.service.StatisticsService;
import com.qingfeng.cms.domain.dict.enums.DictDepartmentEnum;
import com.qingfeng.cms.domain.statistics.ro.EnumsRo;
import com.qingfeng.cms.domain.statistics.vo.ClassModuleVo;
import com.qingfeng.cms.domain.statistics.vo.ClazzCreditsVo;
import com.qingfeng.cms.domain.statistics.vo.CommunitySituationVo;
import com.qingfeng.cms.domain.statistics.vo.GradeScoreVo;
import com.qingfeng.cms.domain.statistics.vo.OrganizeActiveVo;
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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    public R<ClassModuleVo> classModule() {
        ClassModuleVo classModuleVo = statisticsService.classModule(getUserId());
        return success(classModuleVo);
    }

    @ApiOperation(value = "班级的学生学分修读情况", notes = "班级的学生学分修读情况")
    @GetMapping("/clazz/credits")
    @SysLog("班级的学生学分修读情况")
    public R<List<ClazzCreditsVo>> clazzCredits() {
        List<ClazzCreditsVo> clazzCredits = statisticsService.clazzCredits();
        return success(clazzCredits);
    }

    @ApiOperation(value = "根据学生Id，查询学生第二课堂参与情况", notes = "根据学生Id，查询学生第二课堂参与情况")
    @GetMapping("/clazz/stuId/{stuId}")
    @SysLog("根据学生Id，查询学生第二课堂参与情况")
    public R<StuModuleDataAnalysisVo> clazzStuId(@PathVariable("stuId") Long stuId) {
        StuModuleDataAnalysisVo studentScoreTotalVo = statisticsService.clazzStuId(stuId);
        return success(studentScoreTotalVo);
    }

    @ApiOperation(value = "各年级学分修读情况", notes = "各年级学分修读情况")
    @GetMapping("/grade/score")
    @SysLog("各年级学分修读情况")
    public R<GradeScoreVo> gradeScore() {
        // 学院下每个年级（优、良、差）
        GradeScoreVo gradeScoreVo = statisticsService.gradeScore(getUserId());
        return success(gradeScoreVo);
    }

    @ApiOperation(value = "各年级各模块参与人数", notes = "各年级各模块参与人数")
    @GetMapping("/grade/module/{grade}")
    @SysLog("各年级各模块参与人数")
    public R<GradeScoreVo> gradeModule(@PathVariable("grade") String grade) {
        GradeScoreVo gradeScoreVo = statisticsService.gradeModule(getUserId(), grade);
        return success(gradeScoreVo);
    }

    @ApiOperation(value = "返回各年级集合", notes = "返回各个年级集合")
    @GetMapping("/grade/list")
    @SysLog("返回各个年级集合")
    public R<List<String>> gradeList() {
        List<String> gradeList = statisticsService.gradeList(getUserId());
        return success(gradeList);
    }

    @ApiOperation(value = "社团活动举办情况", notes = "社团活动举办情况")
    @GetMapping("/organize/active")
    @SysLog("社团活动举办情况")
    public R<OrganizeActiveVo> organizeActive() {
        OrganizeActiveVo organizeActiveVo = statisticsService.organizeActive(getUserId());
        return success(organizeActiveVo);
    }

    @ApiOperation(value = "每学期社团活动评分详情", notes = "每学期社团活动评分详情")
    @GetMapping("/activity/score/{schoolYear}")
    public R<List<Integer>> activityScore(@PathVariable("schoolYear") String schoolYear) {
        List<Integer> activityScoreVo = statisticsService.activityScore(schoolYear, getUserId());
        return success(activityScoreVo);
    }

    @ApiOperation(value = "根据年份获取学年-学期集合", notes = "根据年份获取学年-学期集合")
    @GetMapping("/anno/schoolYear/{year}")
    public R getSchoolYear(@PathVariable("year") Integer year) {
        List<String> list = new ArrayList<>();
        //获取当前年份
        year -= 4;
        for (int i = 0; i < 6; i++) {
            list.add(year + "-" + (year + 1) + "  第一学期");
            list.add(year + "-" + (year + 1) + "  第二学期");
            year++;
        }
        return success(list);
    }

    @ApiOperation(value = "查询每个社团的活动举办情况", notes = "查询每个社团的活动举办情况")
    @GetMapping("/community/situation/{academicYear}")
    @SysLog("查询每个社团的活动举办情况")
    public R<CommunitySituationVo> communitySituation(@PathVariable("academicYear") String academicYear) {
        CommunitySituationVo communitySituationVo = statisticsService.communitySituation(academicYear);
        return success(communitySituationVo);
    }

    @ApiOperation(value = "根据年份获取学年集合", notes = "根据年份获取学年集合")
    @GetMapping("/anno/academic/{year}")
    public R academicYear(@PathVariable("year") Integer year) {
        List<String> list = new ArrayList<>();
        year -= 4;
        for (int i = 0; i < 6; i++) {
            list.add(year + "-" + (year + 1));
            year++;
        }
        return success(list);
    }

    @ApiOperation(value = "根据社团名和学年查询社团活动质量分析情况", notes = "根据社团名和学年查询社团活动质量分析情况")
    @GetMapping("/community/{orgName}/{schoolYear}")
    @SysLog("根据社团名和学年查询社团活动质量分析情况")
    public R<List<Integer>> evaluationQuality(
            @PathVariable("orgName") String orgName,
            @PathVariable("schoolYear") String schoolYear) {
        List<Integer> evaluationNum = statisticsService.evaluationQuality(orgName, schoolYear);
        return success(evaluationNum);
    }

    @ApiOperation(value = "查询所有学院的各年级修读情况", notes = "查询所有学院的各年级修读情况")
    @GetMapping("/find/dep/all/module/{dictDep}")
    @SysLog("查询所有学院的各年级修读情况")
    public R<GradeScoreVo> findDepAllModule(@PathVariable("dictDep") String dictDep){
        GradeScoreVo gradeScoreVo = statisticsService.findDepAllModule(dictDep);
        return success(gradeScoreVo);
    }

    @ApiOperation(value = "查询学院个年级学生模块修读情况", notes = "查询学院个年级学生模块修读情况")
    @GetMapping("/dep/stu/{depName}/{grade}")
    @SysLog("查询学院个年级学生模块修读情况")
    public R<GradeScoreVo> findDepStuByDepName(@PathVariable("depName") String depName,
                                 @PathVariable("grade") String grade){
        GradeScoreVo gradeScoreVo = statisticsService.findDepStuByDepName(depName, grade);
        return success(gradeScoreVo);
    }

    @ApiOperation(value = "封装返回学院枚举值", notes = "封装返回学院枚举值")
    @GetMapping("/anno/dep/enums")
    public R depEnums(){
        return success(
                Arrays.stream(DictDepartmentEnum.values())
                        .map(d -> {
                            if (!d.name().equals(DictDepartmentEnum.PZHU.name())) {
                                return EnumsRo.builder()
                                        .label(d.getDesc())
                                        .value(d.name())
                                        .build();
                            }
                            return null;
                        })
                        .filter(Objects::nonNull)
                        .collect(Collectors.toList())
        );
    }

    @ApiOperation(value = "返回不同学院各个年级信息", notes = "返回不同学院各个年级信息")
    @GetMapping("/anno/grade/list/{depName}")
    public R<List<String>> gradeListByDepName(@PathVariable("depName") String depName) {
        List<String> gradeList = statisticsService.gradeListByDepName(depName);
        return success(gradeList);
    }
}