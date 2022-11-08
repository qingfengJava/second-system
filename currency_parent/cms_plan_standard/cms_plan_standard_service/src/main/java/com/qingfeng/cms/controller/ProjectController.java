package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.project.service.ProjectService;
import com.qingfeng.cms.domain.project.dto.ProjectSaveDTO;
import com.qingfeng.cms.domain.project.dto.ProjectUpdateDTO;
import com.qingfeng.cms.domain.project.entity.ProjectEntity;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.base.entity.SuperEntity;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

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

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {

        return success();
    }


    /**
     * 信息
     */
    @GetMapping("/info/{projectId}")
    public R info(@PathVariable("projectId") Long projectId) {
        ProjectEntity project = projectService.getById(projectId);

        return success(project);
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

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] projectIds) {
        projectService.removeByIds(Arrays.asList(projectIds));

        return success();
    }

}
