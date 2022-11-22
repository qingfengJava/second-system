package com.qingfeng.cms.biz.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.news.entity.NewsNotifyEntity;
import org.springframework.stereotype.Repository;

/**
 * 消息通知表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Repository
public interface NewsNotifyDao extends BaseMapper<NewsNotifyEntity> {
	
}
