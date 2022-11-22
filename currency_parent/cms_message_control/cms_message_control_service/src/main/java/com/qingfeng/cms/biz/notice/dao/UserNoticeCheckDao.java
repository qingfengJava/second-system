package com.qingfeng.cms.biz.notice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.notice.entity.UserNoticeCheckEntity;
import org.springframework.stereotype.Repository;

/**
 * 用户公告情况表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Repository
public interface UserNoticeCheckDao extends BaseMapper<UserNoticeCheckEntity> {
	
}
