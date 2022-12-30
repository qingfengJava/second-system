package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.student.service.StuInfoService;
import com.qingfeng.cms.domain.student.dto.StuInfoSaveDTO;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
import com.qingfeng.cms.domain.student.enums.EducationalSystemEnum;
import com.qingfeng.cms.domain.student.enums.HuKouTypeEnum;
import com.qingfeng.cms.domain.student.enums.PoliticsStatusEnum;
import com.qingfeng.cms.domain.student.enums.StateSchoolEnum;
import com.qingfeng.cms.domain.student.enums.StudentTypeEnum;
import com.qingfeng.cms.domain.student.ro.EnumsRo;
import com.qingfeng.cms.domain.student.vo.EnumsVoList;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
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
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * 学生信息详情表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供学生信息详情的相关功能", tags = "学生信息详情")
@RequestMapping("/stuinfo")
public class StuInfoController extends BaseController {

    @Autowired
    private StuInfoService stuInfoService;

    @ApiOperation(value = "根据学生用户Id查询学生用户信息详情信息", notes = "根据学生用户Id查询学生用户信息详情信息")
    @GetMapping("/info/{userId}")
    @SysLog("根据学生用户Id查询学生用户信息详情信息")
    public R<StuInfoEntity> info(@PathVariable("userId") Long userId) {
        StuInfoEntity stuInfo = stuInfoService.getOne(Wraps.lbQ(new StuInfoEntity())
                .eq(StuInfoEntity::getUserId, userId));

        return success(stuInfo);
    }

    @ApiOperation(value = "查询学生用户信息详情信息", notes = "查询学生用户信息详情信息")
    @GetMapping
    @SysLog("查询学生用户信息详情信息")
    public R<StuInfoEntity> info() {
        return this.info(getUserId());
    }

    @ApiOperation(value = "保存用户详情信息", notes = "保存用户详情信息")
    @PostMapping("/save")
    @SysLog("保存用户详情信息")
    public R save(@ApiParam(value = "用户详情信息保存实体", required = true)
                  @RequestBody @Validated StuInfoSaveDTO stuInfoSaveDTO) {
        System.out.println(stuInfoSaveDTO);
        stuInfoService.saveStuInfo(stuInfoSaveDTO);

        return success();
    }

    @ApiOperation(value = "返回所有的枚举类型", notes = "返回所有的枚举类型")
    @GetMapping("/find/enums")
    @SysLog("返回所有的枚举类型")
    public R<EnumsVoList> findEnums() {
        return success(EnumsVoList.builder()
                .politics(Arrays.stream(PoliticsStatusEnum.values())
                        .map(e -> EnumsRo
                                .builder()
                                .label(e.getDesc())
                                .value(e.name())
                                .build())
                        .collect(Collectors.toList()))
                .stateSchool(Arrays.stream(StateSchoolEnum.values())
                        .map(e -> EnumsRo
                                .builder()
                                .label(e.getDesc())
                                .value(e.name())
                                .build())
                        .collect(Collectors.toList()))
                .hukou(Arrays.stream(HuKouTypeEnum.values())
                        .map(e -> EnumsRo
                                .builder()
                                .label(e.getDesc())
                                .value(e.name())
                                .build())
                        .collect(Collectors.toList()))
                .stuType(Arrays.stream(StudentTypeEnum.values())
                        .map(e -> EnumsRo
                                .builder()
                                .label(e.getDesc())
                                .value(e.name())
                                .build())
                        .collect(Collectors.toList()))
                .educationalSystem(Arrays.stream(EducationalSystemEnum.values())
                        .map(e -> EnumsRo
                                .builder()
                                .label(e.getDesc())
                                .value(e.name())
                                .build())
                        .collect(Collectors.toList()))
                .build());
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody StuInfoEntity stuInfo) {
        stuInfoService.updateById(stuInfo);

        return success();
    }

}
