package com.qingfeng.service;

import com.qingfeng.entity.Apply;
import com.qingfeng.vo.ResultVO;

/**
 * 活动申请业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/26
 */
public interface ApplyService {

    /**
     * 申请活动
     * @param apply
     * @return
     */
    ResultVO applyActive(Apply apply);

    /**
     * 审核活动申请
     * @param applyId
     * @param isAgree
     * @return
     */
    ResultVO checkApplyActive(Integer applyId,Integer isAgree);

    /**
     * 删除申请的活动
     * @param applyId
     * @return
     */
    ResultVO deleteApplyActive(Integer applyId);

    /**
     * 修改申请活动的信息
     * @param applyId
     * @param apply
     * @return
     */
    ResultVO updateApplyActive(Integer applyId, Apply apply);
}
