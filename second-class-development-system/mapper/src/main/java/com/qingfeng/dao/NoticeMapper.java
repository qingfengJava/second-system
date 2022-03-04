package com.qingfeng.dao;

import com.qingfeng.entity.Notice;
import com.qingfeng.entity.NoticeVo;
import com.qingfeng.generaldao.GeneralDao;
import org.springframework.stereotype.Repository;

/**
 * 系统公告持久层
 *
 * @author 清风学Java
 */
@Repository
public interface NoticeMapper extends GeneralDao<Notice> {

    /**
     * 根据公告表主键Id查询公告详情信息
     *
     * @param noticeId
     * @return
     */
    NoticeVo queryDetails(Integer noticeId);
}