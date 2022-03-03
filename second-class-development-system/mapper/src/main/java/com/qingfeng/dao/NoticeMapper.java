package com.qingfeng.dao;

import com.qingfeng.entity.Notice;
import com.qingfeng.generaldao.GeneralDao;
import org.springframework.stereotype.Repository;

/**
 * 系统公告持久层
 *
 * @author 清风学Java
 */
@Repository
public interface NoticeMapper extends GeneralDao<Notice> {
}