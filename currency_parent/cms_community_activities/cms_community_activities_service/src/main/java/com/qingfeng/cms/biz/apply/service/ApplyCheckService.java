package com.qingfeng.cms.biz.apply.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.apply.dto.ActiveCheckQueryDTO;
import com.qingfeng.cms.domain.apply.dto.ApplyCheckDTO;
import com.qingfeng.cms.domain.apply.dto.ApplyCheckSaveDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyCheckEntity;
import com.qingfeng.cms.domain.apply.vo.ApplyCheckVo;

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

    /**
     * 提交活动终审结果
     * @param applyCheckDTO
     */
    void submitApplyCheck(ApplyCheckDTO applyCheckDTO);

    /**
     * 查询需要进行活动终审的活动列表
     * @param activeCheckQueryDTO
     * @return
     */
    ApplyCheckVo applyCheckList(ActiveCheckQueryDTO activeCheckQueryDTO);
}

