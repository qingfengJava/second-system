package com.qingfeng.service;

import com.qingfeng.vo.ResultVO;

/**
 * 活动评价的业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/4/11
 */
public interface EvaluationService {

    /**
     * 根据用户Id和活动Id查询评价信息
     *
     * @param uid
     * @param applyId
     * @return
     */
    ResultVO selectEvaluationByUid(Integer uid, Integer applyId);
}
