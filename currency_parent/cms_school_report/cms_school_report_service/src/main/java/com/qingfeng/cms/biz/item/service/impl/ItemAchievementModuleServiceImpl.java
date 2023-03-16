package com.qingfeng.cms.biz.item.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.item.dao.ItemAchievementModuleDao;
import com.qingfeng.cms.biz.item.service.ItemAchievementModuleService;
import com.qingfeng.cms.biz.total.dao.StudentScoreTotalDao;
import com.qingfeng.cms.domain.item.dto.ItemAchievementModuleSaveDTO;
import com.qingfeng.cms.domain.item.dto.ItemAchievementModuleUpdateDTO;
import com.qingfeng.cms.domain.item.entity.ItemAchievementModuleEntity;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import com.qingfeng.cms.domain.total.entity.StudentScoreTotalEntity;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.sdk.planstandard.plan.PlanApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

/**
 * 方案模块得分情况
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-15 11:50:15
 */
@Service
public class ItemAchievementModuleServiceImpl extends ServiceImpl<ItemAchievementModuleDao, ItemAchievementModuleEntity> implements ItemAchievementModuleService {

    @Autowired
    private StudentScoreTotalDao studentScoreTotalDao;

    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private PlanApi planApi;


    /**
     * 保存方案模块得分详情
     *
     * @param itemAchievementModuleSaveDTO
     */
    @Override
    public void saveItemAchievementModule(ItemAchievementModuleSaveDTO itemAchievementModuleSaveDTO) {
        // 在调用该接口保存前，同样也是需要
        baseMapper.insert(dozerUtils.map2(itemAchievementModuleSaveDTO, ItemAchievementModuleEntity.class));

        // 查询总得分进行累加
        StudentScoreTotalEntity studentScoreTotal = studentScoreTotalDao.selectOne(
                Wraps.lbQ(new StudentScoreTotalEntity())
                        .eq(StudentScoreTotalEntity::getUserId, itemAchievementModuleSaveDTO.getUserId())
        );

        if (ObjectUtil.isEmpty(studentScoreTotal)) {
            // 查询用户应休学分
            PlanEntity plan = planApi.getPlanByUserId(itemAchievementModuleSaveDTO.getUserId()).getData();

            studentScoreTotalDao.insert(StudentScoreTotalEntity.builder()
                    .userId(itemAchievementModuleSaveDTO.getUserId())
                    .score(itemAchievementModuleSaveDTO.getScore())
                    .creditsScore(BigDecimal.valueOf(plan.getTotalScore()))
                    .build()
            );
        } else {
            // 已存在，就进行修改
            studentScoreTotal.setScore(studentScoreTotal.getScore().add(itemAchievementModuleSaveDTO.getScore()));
            studentScoreTotalDao.updateById(studentScoreTotal);
        }
    }

    /**
     * 取消模块加分申请
     *
     * @param itemAchievementModuleUpdateDTO
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void cancelBonusPoints(ItemAchievementModuleUpdateDTO itemAchievementModuleUpdateDTO) {
        // 查询出当前信息，
        ItemAchievementModuleEntity itemAchievementModule = baseMapper.selectOne(
                Wraps.lbQ(new ItemAchievementModuleEntity())
                        .eq(ItemAchievementModuleEntity::getUserId, itemAchievementModuleUpdateDTO.getUserId())
                        .eq(ItemAchievementModuleEntity::getBonusScoreApplyId, itemAchievementModuleUpdateDTO.getBonusScoreApplyId())
        );

        // 查询用户总学分记录
        StudentScoreTotalEntity studentScoreTotal = studentScoreTotalDao.selectOne(
                Wraps.lbQ(new StudentScoreTotalEntity())
                        .eq(StudentScoreTotalEntity::getUserId, itemAchievementModuleUpdateDTO.getUserId())
        );

        // 修改总学分记录
        studentScoreTotalDao.updateById(studentScoreTotal.setScore(
                studentScoreTotal.getScore().subtract(itemAchievementModule.getScore())
        ));

        // 删除当前记录
        baseMapper.deleteById(itemAchievementModule.getId());
    }
}