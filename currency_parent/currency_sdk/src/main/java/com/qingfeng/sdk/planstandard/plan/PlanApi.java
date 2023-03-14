package com.qingfeng.sdk.planstandard.plan;

import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/14
 */
@FeignClient(value = "cms-plan-standard", fallback = PlanApiFallback.class)
@Component
public interface PlanApi {

    /**
     * 根据学生用户Id查询方案信息
     * @param userId
     * @return
     */
    @GetMapping("/plans/{userId}")
    public R<PlanEntity> getPlanByUserId(@PathVariable("userId") Long userId);

}
