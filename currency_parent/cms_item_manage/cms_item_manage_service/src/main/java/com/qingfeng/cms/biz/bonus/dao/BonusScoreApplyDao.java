package com.qingfeng.cms.biz.bonus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.bonus.dto.BonusScoreApplyPageDTO;
import com.qingfeng.cms.domain.bonus.entity.BonusScoreApplyEntity;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 加分申报表（提交加分细则申请）
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
@Repository
public interface BonusScoreApplyDao extends BaseMapper<BonusScoreApplyEntity> {

    /**
     * 分页查询学生加分申报信息
     * @param bonusScoreApplyPageDTO
     * @return
     */
    List<BonusScoreApplyEntity> findBonusScorePage(@Param("bonusScoreApplyPageDTO") BonusScoreApplyPageDTO bonusScoreApplyPageDTO,
                                                   @Param("userId") Long userId);

    /**
     * 查询学生加分申报信息总记录数
     * @param bonusScoreApplyPageDTO
     * @param userId
     * @return
     */
    Integer findBonusScorePageCount(@Param("bonusScoreApplyPageDTO") BonusScoreApplyPageDTO bonusScoreApplyPageDTO,
                                    @Param("userId") Long userId);
}
