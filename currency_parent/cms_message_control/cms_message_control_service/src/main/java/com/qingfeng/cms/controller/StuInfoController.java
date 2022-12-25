package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.student.service.StuInfoService;
import com.qingfeng.cms.domain.student.dto.StuInfoSaveDTO;
import com.qingfeng.cms.domain.student.entity.StuInfoEntity;
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
    public R<StuInfoEntity> info(@PathVariable("userId") Long userId){
        StuInfoEntity stuInfo = stuInfoService.getOne(Wraps.lbQ(new StuInfoEntity())
                .eq(StuInfoEntity::getUserId, userId));

        return success(stuInfo);
    }

    @ApiOperation(value = "保存用户详情信息", notes = "保存用户详情信息")
    @PostMapping("/save")
    @SysLog("保存用户详情信息")
    public R save(@ApiParam(value = "用户详情信息保存实体", required = true)
                      @RequestBody @Validated StuInfoSaveDTO stuInfoSaveDTO){
		stuInfoService.saveStuInfo(stuInfoSaveDTO);

        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody StuInfoEntity stuInfo){
		stuInfoService.updateById(stuInfo);

        return success();
    }

}
