package com.qingfeng.service;

import com.qingfeng.entity.Apply;
import com.qingfeng.entity.AuditForm;
import com.qingfeng.entity.Check;
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
     * @param uid 用户Id
     * @param apply 申请活动实体类对象
     * @return
     */
    ResultVO applyActive(Integer uid,Apply apply);

    /**
     * 审核活动申请
     * @param applyId
     * @param auditForm
     * @return
     */
    ResultVO checkApplyActive(Integer applyId, AuditForm auditForm);

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

    /**
     * 根据社团用户Id查询该社团一个学年内举办活动的个数
     * @param userId
     * @return
     */
    ResultVO queryActiveYearCount(Integer userId);

    /**
     * 社团联进行活动最终审核
     * @param check
     * @return
     */
    ResultVO finalCheck(Check check);

    /**
     * 根据社团用户Id查询该社团一个学年内失败的活动的个数
     * @param userId
     * @return
     */
    ResultVO queryActiveFailCount(Integer userId);
}
