package com.qingfeng.cms.biz.notice.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.notice.dto.NoticeQueryDTO;
import com.qingfeng.cms.domain.notice.entity.NoticeEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 系统公告表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Repository
public interface NoticeDao extends BaseMapper<NoticeEntity> {

    /**
     * 查询用户自己发布的公告信息
     * @param noticeQueryDTO
     * @param userId
     * @return
     */
    List<NoticeEntity> selectNoticeList(
            @Param("noticeQueryDTO") NoticeQueryDTO noticeQueryDTO,
            @Param("userId") Long userId);
}
