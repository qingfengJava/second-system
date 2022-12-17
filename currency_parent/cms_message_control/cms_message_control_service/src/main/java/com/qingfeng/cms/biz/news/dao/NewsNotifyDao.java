package com.qingfeng.cms.biz.news.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.news.entity.NewsNotifyEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 消息通知表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Repository
public interface NewsNotifyDao extends BaseMapper<NewsNotifyEntity> {

    /**
     * 分页查询用户对应的消息
     * @param pageNo
     * @param pageSize
     * @param userId
     * @return
     */
    List<NewsNotifyEntity> findList(@Param("pageNo") Integer pageNo,
                                    @Param("pageSize") Integer pageSize,
                                    @Param("userId") Long userId);
}
