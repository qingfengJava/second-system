package com.qingfeng.cms.biz.plan.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import org.springframework.stereotype.Repository;

/**
 * 方案设定表（是否是修读标准，本科标准，专科标准）
 * 
 * @author qingfeng
 * @email ${email}
 * @date 2022-10-08 19:44:16
 */
@Repository
public interface PlanDao extends BaseMapper<PlanEntity> {
	
}
