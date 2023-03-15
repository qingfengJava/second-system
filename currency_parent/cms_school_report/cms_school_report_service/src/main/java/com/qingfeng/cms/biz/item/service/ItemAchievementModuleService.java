package com.qingfeng.cms.biz.item.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.item.dto.ItemAchievementModuleSaveDTO;
import com.qingfeng.cms.domain.item.entity.ItemAchievementModuleEntity;

/**
 * 方案模块得分情况
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-15 11:50:15
 */
public interface ItemAchievementModuleService extends IService<ItemAchievementModuleEntity> {

    /**
     * 保存方案模块得分详情
     * @param itemAchievementModuleSaveDTO
     */
    void saveItemAchievementModule(ItemAchievementModuleSaveDTO itemAchievementModuleSaveDTO);
}

