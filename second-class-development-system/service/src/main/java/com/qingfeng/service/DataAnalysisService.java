package com.qingfeng.service;

import com.qingfeng.vo.ResultVO;

/**
 * 数据分析业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/4/12
 */
public interface DataAnalysisService {

    /**
     * 根据用户Id查询学生参与活动信息
     *
     * @return
     */
    ResultVO queryActiveNum(Integer uid);

    /**
     * 根据用户Id查询学生参与各类型活动的数量
     * @param uid
     * @return
     */
    ResultVO queryTypeActiveNum(Integer uid);

    /**
     * 查询学生个年级修的学分
     * @param uid
     * @return
     */
    ResultVO queryScore(Integer uid);
}
