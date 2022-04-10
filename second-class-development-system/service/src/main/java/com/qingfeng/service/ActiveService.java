package com.qingfeng.service;

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
     * 根据学生的Id分页查询学生报名(参与)待参与的活动
     * @param uid
     * @param pageNum
     * @param limit
     * @return
     */
    ResultVO checkRegistration(String uid,int participate,int pageNum,int limit);

    /**
     * 分页查询所有的新活动
     * @param uid
     * @param pageNum
     * @param limit
     * @return
     */
    ResultVO queryApply(Integer uid,int pageNum, int limit);

    /**
     * 根据活动id查询对应活动的详情信息
     * @param applyId
     * @return
     */
    ResultVO queryApplyById(String applyId);

    /**
     * 根据申请活动的Id查询活动的详情信息，包括社团用户，社团组织等信息
     * @param applyId
     * @return
     */
    ResultVO queryApplyDetails(Integer applyId);

    /**
     * 根据申请表Id查询学生报名详情信息
     * @param uid
     * @param applyId
     * @return
     */
    ResultVO queryRegistActiveDetails(Integer uid,Integer applyId);

    /**
     * 根据活动申请表的主键Id查询某个活动报名的总人数Id
     * @param applyId
     * @return
     */
    ResultVO queryRegistCount(Integer applyId);
}
