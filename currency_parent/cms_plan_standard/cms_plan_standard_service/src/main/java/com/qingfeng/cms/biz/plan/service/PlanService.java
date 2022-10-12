package com.qingfeng.cms.biz.plan.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;

/**
 * 方案设定表（是否是修读标准，本科标准，专科标准）
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
public interface PlanService extends IService<PlanEntity> {

    /**
     * 保存方案
     * @param plan
     */
    void savePlan(PlanEntity plan);

    /**
     * 修改方案信息
     * @param plan
     */
    void updatePlan(PlanEntity plan);
}

