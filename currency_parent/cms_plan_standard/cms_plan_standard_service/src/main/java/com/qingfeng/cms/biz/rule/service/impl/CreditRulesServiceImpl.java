package com.qingfeng.cms.biz.rule.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.rule.dao.CreditRulesDao;
import com.qingfeng.cms.biz.rule.enums.CreditRulesExceptionMsg;
import com.qingfeng.cms.biz.rule.service.CreditRulesService;
import com.qingfeng.cms.domain.rule.dto.CreditRulesSaveDTO;
import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;
import com.qingfeng.cms.domain.rule.enums.RuleCheckEnum;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.common.enums.RoleEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import com.qingfeng.sdk.auth.role.RoleApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


/**
 * 加分（学分）细则表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:15
 */
@Service("creditRulesService")
@Slf4j
public class CreditRulesServiceImpl extends ServiceImpl<CreditRulesDao, CreditRulesEntity> implements CreditRulesService {

    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private RoleApi roleApi;

    /**
     * 保存学分细则信息
     * @param creditRulesSaveDTO
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void saveCreditRules(CreditRulesSaveDTO creditRulesSaveDTO, Long userId) {
        checkCreditRules(creditRulesSaveDTO);
        CreditRulesEntity creditRulesEntity = dozerUtils.map2(creditRulesSaveDTO, CreditRulesEntity.class);

        // 并查询当前用户身份，规定学院添加的需要审核
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.STU_OFFICE_ADMIN.name()});
        if (userIdByCode.getData().contains(userId)) {
            //是院级用户
            creditRulesEntity.setIsCheck(RuleCheckEnum.INIT);
        } else {
            creditRulesEntity.setIsCheck(RuleCheckEnum.IS_FINISHED);
        }

        baseMapper.insert(creditRulesEntity);
    }

    private void checkCreditRules(CreditRulesSaveDTO creditRulesSaveDTO) {
        // 检查条件，当前项目下是否已经学分了，若分等级，同一等级下是否已经有学分了
        LbqWrapper<CreditRulesEntity> wrapper = Wraps.lbQ(new CreditRulesEntity())
                .eq(CreditRulesEntity::getLevelId, creditRulesSaveDTO.getLevelId());
        if (ObjectUtil.isNotEmpty(creditRulesSaveDTO.getScoreGrade())) {
            //有等级划分
            wrapper.eq(CreditRulesEntity::getScoreGrade, creditRulesSaveDTO.getScoreGrade());
        }
        CreditRulesEntity creditRulesEntity = baseMapper.selectOne(wrapper);
        if (ObjectUtil.isNotEmpty(creditRulesEntity)){
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), CreditRulesExceptionMsg.IS_EXISTS.getMsg());
        }
    }
}