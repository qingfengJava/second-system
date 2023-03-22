package com.qingfeng.sdk.item.bonusapply;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/22
 */
@FeignClient(value = "cms-item-manage", fallback = BonusScoreApplyApiFallback.class)
@Component
public interface BonusScoreApplyApi {
}
