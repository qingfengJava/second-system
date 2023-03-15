package com.qingfeng.cms.biz.club.service.impl;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.club.dao.ClubScoreModuleDao;
import com.qingfeng.cms.biz.club.service.ClubScoreModuleService;
import com.qingfeng.cms.biz.total.dao.StudentScoreTotalDao;
import com.qingfeng.cms.domain.club.dto.ClubScoreModuleSaveDTO;
import com.qingfeng.cms.domain.club.entity.ClubScoreModuleEntity;
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
 * 社团活动得分情况
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-15 11:50:15
 */
@Service
public class ClubScoreModuleServiceImpl extends ServiceImpl<ClubScoreModuleDao, ClubScoreModuleEntity> implements ClubScoreModuleService {

    @Autowired
    private StudentScoreTotalDao studentScoreTotalDao;

    @Autowired
    private DozerUtils dozerUtils;
    @Autowired
    private PlanApi planApi;

    /**
     * 保存社团得分情况
     *
     * @param clubScoreModuleSaveDTO
     */
    @Override
    @Transactional(rollbackFor = BizException.class)
    public void saveClubScoreModule(ClubScoreModuleSaveDTO clubScoreModuleSaveDTO) {
        // 对于社团活动在评价完成进行加分的时候就要排除重复加分的情况
        baseMapper.insert(dozerUtils.map2(clubScoreModuleSaveDTO, ClubScoreModuleEntity.class));

        // 查询总得分进行累加
        StudentScoreTotalEntity studentScoreTotal = studentScoreTotalDao.selectOne(
                Wraps.lbQ(new StudentScoreTotalEntity())
                        .eq(StudentScoreTotalEntity::getUserId, clubScoreModuleSaveDTO.getUserId())
        );

        if (ObjectUtil.isEmpty(studentScoreTotal)) {
            // 查询用户应休学分
            PlanEntity plan = planApi.getPlanByUserId(clubScoreModuleSaveDTO.getUserId()).getData();

            studentScoreTotalDao.insert(StudentScoreTotalEntity.builder()
                    .userId(clubScoreModuleSaveDTO.getUserId())
                    .score(clubScoreModuleSaveDTO.getScore())
                    .creditsScore(BigDecimal.valueOf(plan.getTotalScore()))
                    .build()
            );
        } else {
            // 已存在，就进行修改
            studentScoreTotal.setScore(studentScoreTotal.getScore().add(clubScoreModuleSaveDTO.getScore()));
            studentScoreTotalDao.updateById(studentScoreTotal);
        }
    }
}