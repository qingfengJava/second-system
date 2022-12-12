package com.qingfeng.cms.biz.news.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.news.dao.NewsNotifyDao;
import com.qingfeng.cms.biz.news.service.NewsNotifyService;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.entity.NewsNotifyEntity;
import com.qingfeng.currency.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 消息通知表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Service
public class NewsNotifyServiceImpl extends ServiceImpl<NewsNotifyDao, NewsNotifyEntity> implements NewsNotifyService {

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 保存消息信息
     * @param newsNotifySaveDTO
     */
    @Override
    public void saveNews(NewsNotifySaveDTO newsNotifySaveDTO) {
        baseMapper.insert(dozerUtils.map2(newsNotifySaveDTO, NewsNotifyEntity.class));
    }
}