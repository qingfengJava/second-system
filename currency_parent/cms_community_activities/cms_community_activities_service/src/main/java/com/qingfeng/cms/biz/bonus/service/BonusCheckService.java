package com.qingfeng.cms.biz.bonus.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.bonus.dto.BonusCheckQueryDTO;
import com.qingfeng.cms.domain.bonus.dto.BonusCheckSaveDTO;
import com.qingfeng.cms.domain.bonus.entity.BonusCheckEntity;
import com.qingfeng.cms.domain.bonus.vo.BonusCheckVo;

import java.io.IOException;

/**
 * 加分文件审核表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-03 11:28:11
 */
public interface BonusCheckService extends IService<BonusCheckEntity> {

    /**
     * 查询需要加分的活动列表
     * @param bonusCheckQueryDTO
     * @return
     */
    BonusCheckVo bonusList(BonusCheckQueryDTO bonusCheckQueryDTO);

    /**
     * 活动加分审核结果保存
     * @param bonusCheckSaveDTO
     */
    void updateBonus(BonusCheckSaveDTO bonusCheckSaveDTO) throws IOException;
}

