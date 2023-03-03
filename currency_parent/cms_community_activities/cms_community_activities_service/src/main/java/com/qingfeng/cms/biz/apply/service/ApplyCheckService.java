package com.qingfeng.cms.biz.apply.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.apply.dto.ApplyCheckSaveDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyCheckEntity;

/**
 * 活动审核表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
public interface ApplyCheckService extends IService<ApplyCheckEntity> {

    /**
     * 提交活动终审资料
     * @param applyCheckSaveDTO
     */
    void saveApplyCheck(ApplyCheckSaveDTO applyCheckSaveDTO);
}

