package com.qingfeng.cms.biz.apply.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.apply.dto.ApplySaveDTO;
import com.qingfeng.cms.domain.apply.dto.ApplyUpdateDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;

/**
 * 社团活动申请表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
public interface ApplyService extends IService<ApplyEntity> {

    /**
     * 活动申请信息保存
     * @param applySaveDTO
     * @param userId
     */
    void saveApply(ApplySaveDTO applySaveDTO, Long userId);

    /**
     * 活动申请信息修改
     * @param applyUpdateDTO
     */
    void updateApplyById(ApplyUpdateDTO applyUpdateDTO);
}

