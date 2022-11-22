package com.qingfeng.cms.biz.feedback.dao;


import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.feedback.entity.SystemFeedbackEntity;
import org.springframework.stereotype.Repository;

/**
 * 系统反馈表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:25
 */
@Repository
public interface SystemFeedbackDao extends BaseMapper<SystemFeedbackEntity> {
	
}
