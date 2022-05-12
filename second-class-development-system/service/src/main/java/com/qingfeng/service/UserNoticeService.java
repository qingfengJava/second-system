package com.qingfeng.service;

import com.qingfeng.vo.ResultVO;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/11
 */
public interface UserNoticeService {

    /**
     * 添加用户公告查看信息
     * @param uid
     * @param noticeId
     * @return
     */
    ResultVO addUserNotice(Integer uid, Integer noticeId);
}
