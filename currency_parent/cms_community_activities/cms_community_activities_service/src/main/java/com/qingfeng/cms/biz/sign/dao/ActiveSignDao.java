package com.qingfeng.cms.biz.sign.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.sign.entity.ActiveSignEntity;
import org.springframework.stereotype.Repository;

/**
 * 活动报名表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Repository
public interface ActiveSignDao extends BaseMapper<ActiveSignEntity> {
	
}
