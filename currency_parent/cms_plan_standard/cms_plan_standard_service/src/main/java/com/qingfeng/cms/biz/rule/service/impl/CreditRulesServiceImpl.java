package com.qingfeng.cms.biz.rule.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.level.service.LevelService;
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
import java.util.stream.Collectors;


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
    @Autowired
    private LevelService levelService;

    /**
     * 保存学分细则信息
     * @param creditRulesSaveDTOList
     * @param userId
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void saveCreditRules(List<CreditRulesSaveDTO> creditRulesSaveDTOList, Long userId) {
        List<CreditRulesEntity> creditRulesEntityList = dozerUtils.mapList(creditRulesSaveDTOList, CreditRulesEntity.class);
        //如果有等级不能存在相同的等级
        if (creditRulesEntityList.size() > 1){
            creditRulesEntityList.stream()
                    .collect(Collectors.groupingBy(
                            CreditRulesEntity::getScoreGrade,
                            Collectors.counting()
                    )).forEach((k,v) -> {
                        if (v > 1){
                            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), CreditRulesExceptionMsg.GRADE_IS_SAME.getMsg());
                        }
                    });
        }

        //删除原有的学分，再重新进行添加
        baseMapper.delete(Wraps.lbQ(new CreditRulesEntity())
                .eq(CreditRulesEntity::getLevelId, creditRulesEntityList.get(0).getLevelId()));

        // 并查询当前用户身份，规定学院添加的需要审核
        R<List<Long>> userIdByCode = roleApi.findUserIdByCode(new String[]{RoleEnum.YUAN_LEVEL_LEADER.name()});
        if (userIdByCode.getData().contains(userId)) {
            //是院级用户
            creditRulesEntityList.forEach(c -> c.setIsCheck(RuleCheckEnum.INIT));
        } else {
            creditRulesEntityList.forEach(c -> c.setIsCheck(RuleCheckEnum.IS_FINISHED));
        }

        this.saveBatch(creditRulesEntityList);

    }

    /**
     * 根据学分Id删除学分和其对应的等级信息
     * @param id
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void removeLevelById(Long id) {
        //首先查询学分详情
        CreditRulesEntity creditRulesEntity = baseMapper.selectById(id);
        //查询对应的等级下面是否对应多个学分，一般是只有一个的
        List<CreditRulesEntity> creditRulesEntityList = baseMapper.selectList(Wraps.lbQ(new CreditRulesEntity())
                .eq(CreditRulesEntity::getLevelId, creditRulesEntity.getLevelId()));
        if (creditRulesEntityList.size() == 1){
            //说明只有一条记录，连同等级直接删除
            levelService.removeById(creditRulesEntity.getLevelId());
        }
        baseMapper.deleteById(id);
    }

    @Deprecated
    private void checkCreditRules(CreditRulesEntity creditRulesEntity) {
        // 检查条件，当前项目下是否已经学分了，若分等级，同一等级下是否已经有学分了
        LbqWrapper<CreditRulesEntity> wrapper = Wraps.lbQ(new CreditRulesEntity())
                .eq(CreditRulesEntity::getLevelId, creditRulesEntity.getLevelId());
        if (ObjectUtil.isNotEmpty(creditRulesEntity.getScoreGrade())) {
            //有等级划分
            wrapper.eq(CreditRulesEntity::getScoreGrade, creditRulesEntity.getScoreGrade());
        }
        CreditRulesEntity creditRules= baseMapper.selectOne(wrapper);
        if (ObjectUtil.isNotEmpty(creditRules)){
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), CreditRulesExceptionMsg.IS_EXISTS.getMsg());
        }
    }
}