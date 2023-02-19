package com.qingfeng.cms.job.apply;

import com.qingfeng.cms.biz.apply.service.ApplyService;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.apply.enums.AgreeStatusEnum;
import com.qingfeng.cms.domain.apply.enums.IsReleaseEnum;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.XxlJob;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 检测活动申请过程中的一些情况
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/18
 */
@Component
@Slf4j
public class ActiveApplyTimingDetection {

    @Autowired
    private ApplyService applyService;

    // TODO 检测申请后一直未审核（给审核方发送消息）

    /**
     * 活动开始前，检测活动申请通过，用户并没有手动进行发布活动的情况，进行活动的一键发布
     * @return
     */
    @XxlJob(value = "activeApplyRelease")
    public ReturnT activeApplyRelease() {
        // 查询出所有符合条件的活动   查询活动申请通过两天以后的，你或者是活动即将开始前三天的
        applyService.list(Wraps.lbQ(new ApplyEntity())
                .eq(ApplyEntity::getAgreeStatus, AgreeStatusEnum.IS_PASSED)
                .eq(ApplyEntity::getIsRelease, IsReleaseEnum.INIT));

        return ReturnT.SUCCESS;
    }

    // TODO 检测活动审核之后超时一周以上未处理，但未过期的活动

    // TODO 检测已经过期的活动

    // TODO 检测活动状态 主要是修改为进行中和已废弃

}
