package com.qingfeng.service;

import com.qingfeng.entity.SystemFeedback;
import com.qingfeng.utils.PageHelper;
import com.qingfeng.vo.ResultVO;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/14
 */
public interface SystemFeedbackService {

    /**
     * 添加课堂反馈信息
     * @param uid
     * @param systemFeedback
     * @return
     */
    ResultVO addFeedback(Integer uid, SystemFeedback systemFeedback);

    /**
     * 分页条件查询反馈信息列表
     * @param uid
     * @param isAdmin
     * @param pageNum
     * @param limit
     * @param isReceive
     * @param feedbackType
     * @return
     */
    PageHelper<SystemFeedback> queryFeedbacks(Integer uid, Integer isAdmin, Integer pageNum, Integer limit, Integer isReceive,Integer feedbackType);

    /**
     * 删除反馈信息
     * @param feedbacks
     * @return
     */
    ResultVO deleteFeedback(Integer[] feedbacks);
}
