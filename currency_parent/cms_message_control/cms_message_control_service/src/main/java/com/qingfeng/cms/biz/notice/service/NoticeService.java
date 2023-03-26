package com.qingfeng.cms.biz.notice.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.notice.dto.NoticeQueryDTO;
import com.qingfeng.cms.domain.notice.dto.NoticeSaveDTO;
import com.qingfeng.cms.domain.notice.dto.NoticeUpdateDTO;
import com.qingfeng.cms.domain.notice.entity.NoticeEntity;
import com.qingfeng.cms.domain.notice.vo.NoticePageVo;
import com.qingfeng.cms.domain.notice.vo.NoticeUserPageVo;

/**
 * 系统公告表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
public interface NoticeService extends IService<NoticeEntity> {

    /**
     * 发布第二课堂公告
     * @param noticeSaveDTO
     * @param userId
     */
    void publishNotice(NoticeSaveDTO noticeSaveDTO, Long userId);

    /**
     * 修改已发布的公告信息
     * @param noticeUpdateDTO
     * @param userId
     */
    void updateNoticeById(NoticeUpdateDTO noticeUpdateDTO, Long userId);

    /**
     * 根据Id查询公告信息
     * @param id
     * @param userId
     * @return
     */
    NoticeEntity getNoticeById(Long id, Long userId);

    /**
     * 根据Id删除第二课堂公告信息
     * @param id
     */
    void deleteNoticeById(Long id);

    /**
     * 查询用户自己发布的公告信息
     * @param noticeQueryDTO
     * @param userId
     * @return
     */
    NoticePageVo noticeList(NoticeQueryDTO noticeQueryDTO, Long userId);

    /**
     * 查询用户可以查看的公告信息
     * @param noticeQueryDTO
     * @param userId
     * @return
     */
    NoticeUserPageVo noticeListByUserId(NoticeQueryDTO noticeQueryDTO, Long userId);
}

