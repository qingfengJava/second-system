package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.level.service.LevelService;
import com.qingfeng.cms.domain.level.dto.LevelSaveDTO;
import com.qingfeng.cms.domain.level.dto.LevelUpdateDTO;
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
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 项目等级表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:15
 */
@RestController
@Slf4j
@Validated
@Api(value = "提供项目等级相关的接口功能", tags = "项目等级模块")
@RequestMapping("/level")
public class LevelController extends BaseController {

    @Autowired
    private LevelService levelService;

    @ApiOperation(value = "保存项目等级信息", notes = "保存项目等级信息")
    @PostMapping("/save")
    @SysLog("保存项目等级信息")
    public R save(@RequestBody @Validated LevelSaveDTO levelSaveDTO){
		levelService.saveLevel(levelSaveDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "根据Id修改等级信息", notes = "根据Id修改等级信息")
    @PutMapping("/update")
    @SysLog("根据id修改等级信息")
    public R update(@ApiParam(value = "项目等级修改模块实体")
                        @RequestBody @Validated(SuperEntity.Update.class) LevelUpdateDTO levelUpdateDTO){
		levelService.updateLevelById(levelUpdateDTO, getUserId());
        return success();
    }

}
