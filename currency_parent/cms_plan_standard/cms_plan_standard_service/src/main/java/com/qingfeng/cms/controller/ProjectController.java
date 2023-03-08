package com.qingfeng.cms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qingfeng.cms.biz.project.service.ProjectService;
import com.qingfeng.cms.domain.project.dto.ProjectCheckDTO;
import com.qingfeng.cms.domain.project.dto.ProjectQueryDTO;
import com.qingfeng.cms.domain.project.dto.ProjectSaveDTO;
import com.qingfeng.cms.domain.project.dto.ProjectUpdateDTO;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.cms.domain.project.vo.ProjectCheckEnumsVo;
import com.qingfeng.cms.domain.project.vo.ProjectListVo;
import com.qingfeng.cms.domain.project.vo.ProjectTypeEnumsVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.base.entity.SuperEntity;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 项目表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
@RestController
@Slf4j
@Validated
@Api(value = "提供项目相关的接口功能", tags = "项目模块")
@RequestMapping("/project")
public class ProjectController extends BaseController {

    @Autowired
    private ProjectService projectService;

    @ApiOperation(value = "查询项目学分列表", notes = "查询项目学分列表")
    @PostMapping("/list")
    @SysLog("查询项目学分列表")
    public R<List<ProjectListVo>> list(@RequestBody @Validated ProjectQueryDTO projectQueryDTO) {
        List<ProjectListVo> list = projectService.findList(projectQueryDTO);
        return success(list);
    }

    @ApiOperation(value = "添加模块项目", notes = "添加模块项目")
    @PostMapping("/save")
    @SysLog("添加模块项目")
    public R save(@ApiParam(value = "方案实体", required = true)
                  @RequestBody @Validated ProjectSaveDTO projectSaveDTO) {
        projectService.saveProject(projectSaveDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "修改模块项目内容", notes = "修改模块项目内容")
    @PutMapping("/update")
    @SysLog("修改模块项目内容")
    public R update(@ApiParam(value = "学分认定模块实体")
                    @RequestBody @Validated(SuperEntity.Update.class) ProjectUpdateDTO projectUpdateDTO) {
        projectService.updateProjectById(projectUpdateDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "删除项目及其对应的等级和学分", notes = "删除项目及其对应的等级和学分")
    @DeleteMapping("/delete")
    @SysLog("删除项目及其对应的等级和学分")
    public R delete(@RequestParam("id") Long id) {
        projectService.removeProjectById(id);
        return success();
    }

    @ApiOperation(value = "返回项目类型枚举", notes = "返回项目类型枚举")
    @GetMapping("/projectType")
    @SysLog("返回项目类型枚举")
    public R<List<ProjectTypeEnumsVo>> getProjectType() {
        List<ProjectTypeEnumsVo> projectTypeList = projectService.getProjectType();
        return success(projectTypeList);
    }

    @ApiOperation(value = "返回项目审核类型枚举", notes = "返回项目审核类型枚举")
    @GetMapping("/projectCheck")
    @SysLog("返回项目审核类型枚举")
    public R<List<ProjectCheckEnumsVo>> getProjectCheck() {
        List<ProjectCheckEnumsVo> projectCheckList = projectService.getProjectCheck();
        return success(projectCheckList);
    }

    @ApiOperation(value = "审核项目信息", notes = "审核项目信息")
    @PostMapping("/check")
    @SysLog("审核项目信息")
    public R checkProject(@ApiParam(value = "项目审核模块实体")
                          @RequestBody @Validated(SuperEntity.Update.class) ProjectCheckDTO projectCheckDTO) throws JsonProcessingException {
        projectService.checkProject(projectCheckDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "根据项目Id集合查询项目信息", notes = "根据项目Id集合查询项目信息")
    @PostMapping("/info/list")
    public R<List<ProjectEntity>> projectInfoByIds(@RequestBody List<Long> projectIds) {
        return success(projectService.list(Wraps.lbQ(new ProjectEntity())
                .in(ProjectEntity::getId, projectIds)));
    }
}
