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

    /**
     * 根据主键公告主键Id对公告信息进行修改
     * @param noticeId
     * @param notice
     * @return
     */
    ResultVO updateNotice(Integer noticeId, Notice notice);

    /**
     * 根据公告表主键Id查询对应公告表详情信息
     * @param noticeId
     * @return
     */
    ResultVO queryDetails(Integer noticeId);
}
