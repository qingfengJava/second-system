package com.qingfeng.dao;

import com.qingfeng.entity.UserNotice;
import com.qingfeng.generaldao.GeneralDao;
import org.springframework.stereotype.Repository;

/**
 * 用户公告表持久层
 *
 * @author Administrator
 */
@Repository
public interface UserNoticeMapper extends GeneralDao<UserNotice> {

}