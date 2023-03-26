package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.college.service.CollegeInformationService;
import com.qingfeng.cms.domain.college.dto.CollegeInformationSaveDTO;
import com.qingfeng.cms.domain.college.dto.CollegeInformationUpdateDTO;
import com.qingfeng.cms.domain.college.entity.CollegeInformationEntity;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 院级信息（包含班级），数据字典
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供院级信息的相关功能", tags = "院级信息（包含班级），数据字典")
@RequestMapping("/collegeinformation")
public class CollegeInformationController extends BaseController {

    @Autowired
    private CollegeInformationService collegeInformationService;


    @ApiOperation(value = "根据用户Id查询用户关联的二级学院的信息", notes = "根据用户Id查询用户关联的二级学院的信息")
    @GetMapping("/info/{userId}")
    @SysLog("根据用户Id查询用户关联的二级学院的信息")
    public R<CollegeInformationEntity> info(@ApiParam(value = "用户Id", required = true)
                                            @PathVariable("userId") @NotNull Long userId) {
        CollegeInformationEntity collegeInformation = collegeInformationService.getByUserId(userId);
        return success(collegeInformation);
    }

    @ApiOperation(value = "保存用户关联的二级学院的信息", notes = "保存用户关联的二级学院的信息")
    @PostMapping("/save")
    @SysLog("保存用户关联的二级学院的信息")
    public R save(@ApiParam(value = "院系信息保存实体", required = true)
                  @RequestBody @Validated CollegeInformationSaveDTO collegeInformationSaveDTO) {
        collegeInformationService.saveCollegeInformation(collegeInformationSaveDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "修改用户关联的二级学院的信息", notes = "修改用户关联的二级学院的信息")
    @PutMapping("/update")
    @SysLog("修改用户关联的二级学院的信息")
    public R update(@ApiParam(value = "数据字典实体")
                    @RequestBody @Validated(SuperEntity.Update.class) CollegeInformationUpdateDTO collegeInformationUpdateDTO) {
        collegeInformationService.updateCollegeInformationById(collegeInformationUpdateDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "根据二级学院领导id查询学院下的学生信息", notes = "根据二级学院领导id查询学院下的学生信息")
    @GetMapping("/user/{userId}")
    @SysLog("根据二级学院领导id查询学院下的学生信息")
    public R<List<StuInfoEntity>> getUserInfoList(@ApiParam(value = "二级学院领导Id", required = true)
                                                  @PathVariable("userId") @NotNull Long userId) {
        List<StuInfoEntity> stuInfoList = collegeInformationService.getUserInfoList(userId);
        return success(stuInfoList);
    }

    @ApiOperation(value = "根据学院编码，查询学院用户Id", notes = "根据学院编码，查询学院用户Id")
    @GetMapping("/dep/info")
    @SysLog("根据学院编码，查询学院用户Id")
    public R<List<Long>> depInfo(@RequestParam String organizationCode) {
        return success(
                collegeInformationService.list(
                                Wraps.lbQ(new CollegeInformationEntity())
                                        .eq(CollegeInformationEntity::getOrganizationCode, organizationCode)
                        ).stream()
                        .map(CollegeInformationEntity::getUserId)
                        .collect(Collectors.toList())
        );
    }
}
