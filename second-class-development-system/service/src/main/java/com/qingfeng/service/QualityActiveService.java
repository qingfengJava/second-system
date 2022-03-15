package com.qingfeng.service;

import com.qingfeng.entity.QualityActive;
import com.qingfeng.vo.ResultVO;

/**
 * 精品活动业务层接口
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/3/14
 */
public interface QualityActiveService {

    /**
     * 根据社团组织Id在精品活动快照表里查询该社团一学年精品活动的个数
     * @param organizeId
     * @return
     */
    ResultVO queryApplyQualityActiveCount(Integer organizeId);

    /**
     * 申请精品活动
     * @param qualityActive
     * @return
     */
    ResultVO addQualityActive(QualityActive qualityActive);
}
