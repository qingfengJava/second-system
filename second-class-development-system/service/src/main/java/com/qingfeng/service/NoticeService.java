package com.qingfeng.service;

import com.qingfeng.entity.Notice;
import com.qingfeng.vo.ResultVO;

/**
 * 公告管理业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/4
 */
public interface NoticeService {

    /**
     * 添加公告的方法
     * @param userId
     * @param notice
     * @return
     */
    ResultVO addNotice(Integer userId, Notice notice);
}
