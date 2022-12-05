package com.qingfeng.cms.biz.rule.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.qingfeng.cms.domain.rule.dto.CreditRulesSaveDTO;
import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;

import java.util.List;

/**
 * 加分（学分）细则表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:15
 */
public interface CreditRulesService extends IService<CreditRulesEntity> {

    /**
     * 保存学分细则信息
     * @param creditRulesSaveDTOList
     * @param userId
     */
    void saveCreditRules(List<CreditRulesSaveDTO> creditRulesSaveDTOList, Long userId);

    /**
     * 根据学分Id，删除学分和对应的等级信息
     * @param id
     */
    void removeLevelById(Long id);
}

