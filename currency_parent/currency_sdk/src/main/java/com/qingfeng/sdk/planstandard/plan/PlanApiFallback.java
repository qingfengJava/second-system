package com.qingfeng.sdk.planstandard.plan;

import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import com.qingfeng.currency.base.R;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/14
 */
public class PlanApiFallback implements PlanApi {

    @Override
    public R<PlanEntity> getPlanByUserId(Long userId) {
        return R.fail(R.HYSTRIX_ERROR_MESSAGE);
    }
}
