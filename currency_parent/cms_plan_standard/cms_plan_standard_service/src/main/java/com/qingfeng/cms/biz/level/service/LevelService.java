package com.qingfeng.cms.biz.level.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.level.dto.LevelSaveDTO;
import com.qingfeng.cms.domain.level.dto.LevelUpdateDTO;
import com.qingfeng.cms.domain.level.entity.LevelEntity;

/**
 * 项目等级表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:15
 */
public interface LevelService extends IService<LevelEntity> {

    /**
     * 保存项目等级信息
     * @param levelSaveDTO
     * @param userId
     */
    LevelEntity saveLevel(LevelSaveDTO levelSaveDTO, Long userId);

    /**
     * 根据Id修改项目等级信息
     * @param levelUpdateDTO
     * @param userId
     */
    LevelEntity updateLevelById(LevelUpdateDTO levelUpdateDTO, Long userId);
}

