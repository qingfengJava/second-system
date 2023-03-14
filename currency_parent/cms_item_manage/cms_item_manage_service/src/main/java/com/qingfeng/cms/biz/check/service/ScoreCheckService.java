package com.qingfeng.cms.biz.check.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.check.dto.BonusScoreApplyCheckPageDTO;
import com.qingfeng.cms.domain.check.entity.ScoreCheckEntity;
import com.qingfeng.cms.domain.check.vo.BonusScoreCheckPageVo;
import com.qingfeng.cms.domain.check.vo.PlanModuleVo;

import java.util.List;

/**
 * 加分审核表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
public interface ScoreCheckService extends IService<ScoreCheckEntity> {

    /**
     * 查询不同用户需要审核的项目加分信息
     * @param bonusScoreApplyPageDTO
     * @param userId
     * @return
     */
    BonusScoreCheckPageVo listBonusScore(BonusScoreApplyCheckPageDTO bonusScoreApplyPageDTO, Long userId);

    /**
     * 查询方案模块信息
     * @param userId
     * @return
     */
    List<PlanModuleVo> findPlanModuleList(Long userId);
}

