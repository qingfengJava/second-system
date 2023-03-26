package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.clazz.service.ClazzInfoService;
import com.qingfeng.cms.domain.clazz.dto.ClazzInfoDTO;
import com.qingfeng.cms.domain.clazz.dto.ClazzInfoSaveDTO;
import com.qingfeng.cms.domain.clazz.entity.ClazzInfoEntity;
import com.qingfeng.cms.domain.clazz.ro.EnumsRo;
import com.qingfeng.cms.domain.clazz.vo.UserVo;
import com.qingfeng.cms.domain.student.enums.EducationalSystemEnum;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 班级信息
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-12 22:37:25
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供班级信息的相关功能", tags = "提供班级信息的相关功能")
@RequestMapping("/clazz_info")
public class ClazzInfoController extends BaseController {

    @Autowired
    private ClazzInfoService clazzInfoService;

    @ApiOperation(value = "根据用户Id查询班级信息", notes = "根据用户id查询班级信息")
    @GetMapping("/info")
    @SysLog("根据Id查询班级信息")
    public R<ClazzInfoEntity> info() {
        ClazzInfoEntity clazzInfo = clazzInfoService.getOne(Wraps.lbQ(new ClazzInfoEntity())
                .eq(ClazzInfoEntity::getUserId, getUserId()));
        return success(clazzInfo);
    }

    @ApiOperation(value = "根据院系，年级，班级，专业查询班级信息", notes = "根据院系，年级，班级，专业查询班级信息")
    @PostMapping("/info/user")
    @SysLog("根据院系，年级，班级，专业查询班级信息")
    public R<ClazzInfoEntity> classInfo(@RequestBody @Validated ClazzInfoDTO clazzInfoDTO) {
        return success(
                clazzInfoService.getOne(
                        Wraps.lbQ(new ClazzInfoEntity())
                                .eq(ClazzInfoEntity::getDepartment, clazzInfoDTO.getDepartment())
                                .eq(ClazzInfoEntity::getClazz, clazzInfoDTO.getClazz())
                                .eq(ClazzInfoEntity::getGrade, clazzInfoDTO.getGrade())
                                .eq(ClazzInfoEntity::getMajor, clazzInfoDTO.getMajor())
                )
        );
    }

    @ApiOperation(value = "保存/修改班级信息", notes = "保存/修改班级信息")
    @PostMapping("/save")
    public R saveClazzInfo(@RequestBody @Validated ClazzInfoSaveDTO clazzInfoSaveDTO) {
        clazzInfoService.saveClazzInfo(clazzInfoSaveDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "返回所有的枚举类型", notes = "返回所有的枚举类型")
    @GetMapping("/anno/find/enums")
    @SysLog("返回所有的枚举类型")
    public R findEnums() {
        return success(Arrays.stream(EducationalSystemEnum.values())
                .map(e -> EnumsRo
                        .builder()
                        .label(e.getDesc())
                        .value(e.name())
                        .build())
                .collect(Collectors.toList())
        );
    }

    @ApiOperation(value = "查询班级下的学生信息", notes = "查询班级下的学生信息")
    @GetMapping("/stu/list")
    @SysLog("查询班级下的学生信息")
    public R<List<UserVo>> stuList() {
        return success(clazzInfoService.stuList(getUserId()));
    }
}
