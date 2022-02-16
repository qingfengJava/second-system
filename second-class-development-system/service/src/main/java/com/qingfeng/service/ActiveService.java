package com.qingfeng.service;

import com.qingfeng.entity.Apply;
import com.qingfeng.entity.Regist;
import com.qingfeng.vo.ResultVO;

/**
 * 活动申请业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/2/14
 */
public interface ActiveService {

    /**
     * 申请活动
     * @param apply
     * @return
     */
    ResultVO applyActive(Apply apply);

    /**
     * 活动报名
     * @param applyId
     * @param regist
     * @return
     */
    ResultVO registrationActive(Integer applyId,Regist regist);

    /**
     * 根据学生的Id分页查询学生报名待参与的活动
     * @param uid
     * @param pageNum
     * @param limit
     * @return
     */
    ResultVO checkRegistration(String uid,int pageNum,int limit);

    /**
     * 分页查询所有的新活动
     * @param pageNum
     * @param limit
     * @return
     */
    ResultVO queryApply(int pageNum, int limit);
}
