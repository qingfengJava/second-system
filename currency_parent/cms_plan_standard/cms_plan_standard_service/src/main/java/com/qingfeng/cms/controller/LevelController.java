package com.qingfeng.cms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qingfeng.cms.biz.level.service.LevelService;
import com.qingfeng.cms.domain.level.dto.LevelCheckDTO;
import com.qingfeng.cms.domain.level.dto.LevelSaveDTO;
import com.qingfeng.cms.domain.level.dto.LevelUpdateDTO;
import com.qingfeng.cms.domain.level.entity.LevelEntity;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public R<List<LevelEntity>> save(@RequestBody @Validated List<LevelSaveDTO> levelSaveDTO) {
        List<LevelEntity> levelEntityList = levelService.saveLevel(levelSaveDTO, getUserId());
        return success(levelEntityList);
    }

    @ApiOperation(value = "根据Id修改等级信息", notes = "根据Id修改等级信息")
    @PutMapping("/update")
    @SysLog("根据id修改等级信息")
    public R update(@ApiParam(value = "项目等级修改模块实体")
                    @RequestBody @Validated(SuperEntity.Update.class) LevelUpdateDTO levelUpdateDTO) {
        LevelEntity levelEntity = levelService.updateLevelById(levelUpdateDTO, getUserId());
        return success(levelEntity);
    }

    @ApiOperation(value = "根据Id删除等级及其对应的学分", notes = "根据Id删除等级及其对应的学分")
    @DeleteMapping("/delete")
    @SysLog("根据Id删除等级及其对应的学分")
    public R delete(@RequestParam("id") Long id) {
        levelService.removeLevelById(id);
        return success();
    }

    @ApiOperation(value = "项目等级审核", notes = "项目等级审核")
    @PostMapping("/check")
    @SysLog("项目等级审核")
    public R checkLevel(@ApiParam(value = "项目等级审核实体", required = true)
                        @RequestBody @Validated(SuperEntity.Update.class) LevelCheckDTO levelCheckDTO) throws JsonProcessingException {
        levelService.checkLevel(levelCheckDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "根据等级id集合查询等级信息", notes = "根据等级id集合查询等级信息")
    @PostMapping("/info/list")
    public R<List<LevelEntity>> levelInfoByIds(@RequestBody List<Long> levelIds) {
        return success(levelService.list(Wraps.lbQ(new LevelEntity())
                        .in(LevelEntity::getId, levelIds)
                )
        );
    }

    @ApiOperation(value = "根据Id查询等级信息", notes = "根据Id查询等级信息")
    @GetMapping("/info/{levelId}")
    public R<LevelEntity> levelInfoById(@PathVariable("levelId") Long levelId) {
        return success(levelService.getById(levelId));
    }

}
