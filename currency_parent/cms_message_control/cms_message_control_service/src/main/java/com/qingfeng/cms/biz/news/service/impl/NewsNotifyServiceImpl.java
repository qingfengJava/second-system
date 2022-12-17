package com.qingfeng.cms.biz.news.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.news.dao.NewsNotifyDao;
import com.qingfeng.cms.biz.news.service.NewsNotifyService;
import com.qingfeng.cms.domain.news.dto.NewsNotifySaveDTO;
import com.qingfeng.cms.domain.news.entity.NewsNotifyEntity;
import com.qingfeng.cms.domain.news.vo.NewsNotifyListVo;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    private NewsNotifyDao newsNotifyDao;

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 保存消息信息
     *
     * @param newsNotifySaveDTO
     */
    @Override
    public void saveNews(NewsNotifySaveDTO newsNotifySaveDTO) {
        baseMapper.insert(dozerUtils.map2(newsNotifySaveDTO, NewsNotifyEntity.class));
    }

    /**
     * 分页查询用户的系统消息通知
     * 1、未查看的消息按照时间倒序排列，已查看的按时间倒序排列
     *
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return
     */
    @Override
    public NewsNotifyListVo findList(Integer pageNo, Integer pageSize, Long userId) {
        //先查询总记录数
        Integer count = baseMapper.selectCount(Wraps.lbQ(new NewsNotifyEntity())
                .eq(NewsNotifyEntity::getUserId, userId));
        if (count == 0) {
            return null;
        }

        //确保有数据，我们再做后序的查找
        List<NewsNotifyEntity> newsNotifyEntityList = newsNotifyDao.findList((pageNo - 1) * pageSize, pageSize, userId);

        return NewsNotifyListVo.builder()
                .total(count)
                .newsNotifyEntityList(newsNotifyEntityList)
                .build();
    }
}