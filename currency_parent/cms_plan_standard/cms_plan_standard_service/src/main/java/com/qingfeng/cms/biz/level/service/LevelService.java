package com.qingfeng.cms.biz.level.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.qingfeng.cms.domain.level.dto.LevelCheckDTO;
import com.qingfeng.cms.domain.level.dto.LevelSaveDTO;
import com.qingfeng.cms.domain.level.dto.LevelUpdateDTO;
import com.qingfeng.cms.domain.level.entity.LevelEntity;
import com.qingfeng.sdk.sms.email.domain.EmailEntity;

import java.util.List;

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
     * @return List<LevelEntity>
     */
    List<LevelEntity> saveLevel(List<LevelSaveDTO> levelSaveDTO, Long userId);

    /**
     * 根据Id修改项目等级信息
     * @param levelUpdateDTO
     * @param userId
     */
    LevelEntity updateLevelById(LevelUpdateDTO levelUpdateDTO, Long userId);

    /**
     * 根据id删除等级和对应的学分
     * @param id
     */
    void removeLevelById(Long id);

    /**
     * 审核项目等级
     * @param levelCheckDTO
     * @param userId
     * @throws JsonProcessingException
     */
    void checkLevel(LevelCheckDTO levelCheckDTO, Long userId) throws JsonProcessingException;

    /**
     * 消息发送失败，给自己发送提醒
     * @param emailEntity
     */
    void sendMessageToSlfe(EmailEntity emailEntity);
}

