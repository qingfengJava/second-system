package com.qingfeng.cms.biz.club.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.club.dto.ClubScoreModuleSaveDTO;
import com.qingfeng.cms.domain.club.entity.ClubScoreModuleEntity;

/**
 * 社团活动得分情况
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-15 11:50:15
 */
public interface ClubScoreModuleService extends IService<ClubScoreModuleEntity> {

    /**
     * 保存社团得分情况
     * @param clubScoreModuleSaveDTO
     */
    void saveClubScoreModule(ClubScoreModuleSaveDTO clubScoreModuleSaveDTO);
}

