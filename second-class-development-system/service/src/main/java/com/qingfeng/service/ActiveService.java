package com.qingfeng.service;

import com.qingfeng.entity.Apply;
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
}
