package com.qingfeng.cms.biz.news.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.news.dto.NewsNotifyQueryDTO;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.entity.NewsNotifyEntity;
import com.qingfeng.cms.domain.news.vo.NewsNotifyListVo;

/**
 * 消息通知表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
public interface NewsNotifyService extends IService<NewsNotifyEntity> {

    /**
     * 保存消息实体
     * @param newsNotifySaveDTO
     */
    void saveNews(NewsNotifySaveDTO newsNotifySaveDTO);

    /**
     * 分页查询用户的系统消息通知
     * @param pageNo
     * @param pageSize
     * @param userId
     * @param newsNotifyQueryDTO
     * @return
     */
    NewsNotifyListVo findList(Integer pageNo, Integer pageSize, Long userId, NewsNotifyQueryDTO newsNotifyQueryDTO);
}

