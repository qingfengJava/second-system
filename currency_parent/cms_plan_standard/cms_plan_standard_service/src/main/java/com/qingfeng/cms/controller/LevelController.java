package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.level.service.LevelService;
import com.qingfeng.cms.domain.level.dto.LevelSaveDTO;
import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

        return success();
    }


    /**
     * 信息
     */
    @GetMapping("/info/{levelId}")
    public R info(@PathVariable("levelId") Long levelId){
		LevelEntity level = levelService.getById(levelId);

        return success(level);
    }

    @ApiOperation(value = "保存项目等级信息", notes = "保存项目等级信息")
    @PostMapping("/save")
    @SysLog("保存项目等级信息")
    public R save(@RequestBody @Validated LevelSaveDTO levelSaveDTO){
		levelService.saveLevel(levelSaveDTO, getUserId());
        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody LevelEntity level){
		levelService.updateById(level);

        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] levelIds){
		levelService.removeByIds(Arrays.asList(levelIds));

        return success();
    }

}
