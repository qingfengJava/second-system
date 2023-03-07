package com.qingfeng.cms.biz.check.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.check.entity.ScoreCheckEntity;
import org.springframework.stereotype.Repository;

/**
 * 加分审核表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
@Repository
public interface ScoreCheckDao extends BaseMapper<ScoreCheckEntity> {
	
}
