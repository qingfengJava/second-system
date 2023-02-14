package com.qingfeng.cms.biz.apply.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.apply.dao.ApplyDao;
import com.qingfeng.cms.biz.apply.enums.ApplyExceptionMsg;
import com.qingfeng.cms.biz.apply.service.ApplyService;
import com.qingfeng.cms.domain.apply.dto.ApplySaveDTO;
import com.qingfeng.cms.domain.apply.dto.ApplyUpdateDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.apply.enums.ActiveStatusEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveTypeEnum;
import com.qingfeng.cms.domain.apply.enums.AgreeStatusEnum;
import com.qingfeng.cms.domain.apply.enums.IsReleaseEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 社团活动申请表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Service
public class ApplyServiceImpl extends ServiceImpl<ApplyDao, ApplyEntity> implements ApplyService {

    @Autowired
    private DozerUtils dozerUtils;

    /**
     * 活动申请信息保存
     *
     * @param applySaveDTO
     * @param userId
     */
    @Override
    public void saveApply(ApplySaveDTO applySaveDTO, Long userId) {
        //同一学期，同一活动不能重复申请
        List<ApplyEntity> applyEntityList = getApplyEntities(applySaveDTO.getActiveName(), applySaveDTO.getSchoolYear());

        if (CollUtil.isEmpty(applyEntityList)) {
            //说明没有重复的活动
            ApplyEntity applyEntity = dozerUtils.map2(applySaveDTO, ApplyEntity.class);

            //校验日期的合法性
            inspectionTime(applyEntity);

            applyEntity.setApplyUserId(userId)
                    .setActiveType(ActiveTypeEnum.COMMUNITY_WORK)
                    .setAgreeStatus(AgreeStatusEnum.INIT)
                    .setActiveStatus(ActiveStatusEnum.INIT)
                    .setIsRelease(IsReleaseEnum.INIT)
                    .setActiveApplyTime(LocalDateTime.now());
            baseMapper.insert(applyEntity);
        } else {
            //抛出活动重复的异常
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), ApplyExceptionMsg.REPETITION_OF_CLASSMATE_ACTIVITIES.getMsg());
        }
    }

    /**
     * 活动申请信息修改
     *
     * @param applyUpdateDTO
     */
    @Override
    public void updateApplyById(ApplyUpdateDTO applyUpdateDTO) {
        //同一学期，同一活动不能重复申请
        List<ApplyEntity> applyEntityList = getApplyEntities(applyUpdateDTO.getActiveName(), applyUpdateDTO.getSchoolYear());

        if (CollUtil.isEmpty(applyEntityList)) {
            //说明没有重复的活动
            ApplyEntity applyEntity = dozerUtils.map2(applyUpdateDTO, ApplyEntity.class);

            //校验日期的合法性
            inspectionTime(applyEntity);

            applyEntity.setActiveType(ActiveTypeEnum.COMMUNITY_WORK)
                    .setAgreeStatus(AgreeStatusEnum.INIT)
                    .setActiveStatus(ActiveStatusEnum.INIT)
                    .setIsRelease(IsReleaseEnum.INIT);
            baseMapper.updateById(applyEntity);
        } else {
            //抛出活动重复的异常
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), ApplyExceptionMsg.REPETITION_OF_CLASSMATE_ACTIVITIES.getMsg());
        }
    }

    private void inspectionTime(ApplyEntity applyEntity) {
        if (applyEntity.getActiveStartTime().toEpochDay() - LocalDate.now().toEpochDay() < 7) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "活动必须提前一周时间申请");
        }
        // 活动开始时间，必须小于等于活动结束时间  isBefore：比较第一个日期是否在第二个日期之前
        if (applyEntity.getActiveEndTime().isBefore(applyEntity.getActiveStartTime())) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "活动开始时间必须小于等于活动结束时间");
        }
        if (applyEntity.getActiveStartTime().isBefore(applyEntity.getRegistrationDeadTime())) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "活动报名截止时间必须小于等于活动开始时间");
        }
        if (applyEntity.getRegistrationDeadTime().isBefore(LocalDate.now())) {
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), "活动报名截止时间必须大于当前时间");
        }
    }

    private List<ApplyEntity> getApplyEntities(String activeName, String schoolYear) {
        return baseMapper.selectList(Wraps.lbQ(new ApplyEntity())
                .eq(ApplyEntity::getActiveName, activeName)
                .eq(ApplyEntity::getSchoolYear, schoolYear));
    }
}