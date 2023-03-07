package com.qingfeng.cms.biz.bonus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.bonus.entity.BonusScoreApplyEntity;
import org.springframework.stereotype.Repository;

/**
 * 加分申报表（提交加分细则申请）
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
@Repository
public interface BonusScoreApplyDao extends BaseMapper<BonusScoreApplyEntity> {
	
}
