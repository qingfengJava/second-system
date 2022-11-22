package com.qingfeng.cms.biz.notice.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.notice.dao.NoticeDao;
import com.qingfeng.cms.biz.notice.service.NoticeService;
import com.qingfeng.cms.domain.notice.entity.NoticeEntity;
import org.springframework.stereotype.Service;

/**
 * 系统公告表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Service
public class NoticeServiceImpl extends ServiceImpl<NoticeDao, NoticeEntity> implements NoticeService {

}