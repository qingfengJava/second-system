package com.qingfeng.cms.biz.bonus.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.qingfeng.cms.domain.bonus.dto.BonusCheckQueryDTO;
import com.qingfeng.cms.domain.bonus.entity.BonusCheckEntity;
import com.qingfeng.cms.domain.bonus.ro.BonusCheckRo;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 加分文件审核表
 * 
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-03 11:28:11
 */
@Repository
public interface BonusCheckDao extends BaseMapper<BonusCheckEntity> {

    /**
     * 查询需要加分审核的活动信息
     * @param bonusCheckQueryDTO
     * @return
     */
    List<BonusCheckRo> bonusList(@Param("bonusCheckQueryDTO") BonusCheckQueryDTO bonusCheckQueryDTO);

    /**
     * 查询总记录数
     * @param bonusCheckQueryDTO
     * @return
     */
    Integer selectCountByQuery(@Param("bonusCheckQueryDTO") BonusCheckQueryDTO bonusCheckQueryDTO);
}
