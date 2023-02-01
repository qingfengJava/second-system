package com.qingfeng.cms.biz.apply.service.impl;

import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.qingfeng.cms.biz.apply.dao.ApplyDao;
import com.qingfeng.cms.biz.apply.enums.ApplyExceptionMsg;
import com.qingfeng.cms.biz.apply.service.ApplyService;
import com.qingfeng.cms.domain.apply.dto.ApplySaveDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.apply.enums.ActiveStatusEnum;
import com.qingfeng.cms.domain.apply.enums.AgreeStatusEnum;
import com.qingfeng.cms.domain.apply.enums.IsReleaseEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.dozer.DozerUtils;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
     * @param applySaveDTO
     */
    @Override
    public void saveApply(ApplySaveDTO applySaveDTO) {
        //同一学期，同一活动不能重复申请
        List<ApplyEntity> applyEntityList = baseMapper.selectList(Wraps.lbQ(new ApplyEntity())
                .eq(ApplyEntity::getActiveName, applySaveDTO.getActiveName())
                .eq(ApplyEntity::getSchoolYear, applySaveDTO.getSchoolYear()));

        if (CollUtil.isEmpty(applyEntityList)){
            //说明没有重复的活动
            ApplyEntity applyEntity = dozerUtils.map2(applySaveDTO, ApplyEntity.class);
            applyEntity.setAgreeStatus(AgreeStatusEnum.INIT)
                    .setActiveStatus(ActiveStatusEnum.INIT)
                    .setIsRelease(IsReleaseEnum.INIT);

        }else{
            //抛出活动重复的异常
            throw new BizException(ExceptionCode.SYSTEM_BUSY.getCode(), ApplyExceptionMsg.REPETITION_OF_CLASSMATE_ACTIVITIES.getMsg());
        }
    }
}