package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.item.service.ItemAchievementModuleService;
import com.qingfeng.cms.domain.item.dto.ItemAchievementModuleSaveDTO;
import com.qingfeng.cms.domain.item.dto.ItemAchievementModuleUpdateDTO;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 方案模块得分情况
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-15 11:50:15
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供方案模块得分情况的相关功能", tags = "提供方案模块得分情况的相关功能")
@RequestMapping("/item_achievement_module")
public class ItemAchievementModuleController extends BaseController  {

    @Autowired
    private ItemAchievementModuleService itemAchievementModuleService;

    @ApiOperation(value = "保存方案模块得分情况", notes = "保存方案模块得分情况")
    @PostMapping("/save")
    @SysLog("保存方案模块得分情况")
    public R save(@RequestBody @Validated ItemAchievementModuleSaveDTO itemAchievementModuleSaveDTO){
		itemAchievementModuleService.saveItemAchievementModule(itemAchievementModuleSaveDTO);
        return success();
    }

    /**
     * 存在方案模块误判的情况，只能在最后一级
     *      一旦取消，需要将记录删除，状态更改
     * @return
     */
    @ApiOperation(value = "误判，取消方案模块加分", notes = "误判，取消方案模块加分")
    @PutMapping
    @SysLog("误判、取消方案模块加分")
    public R cancelBonusPoints(@RequestBody @Validated ItemAchievementModuleUpdateDTO itemAchievementModuleUpdateDTO){
        itemAchievementModuleService.cancelBonusPoints(itemAchievementModuleUpdateDTO);
        return success();
    }
}
