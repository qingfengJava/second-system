package com.qingfeng.sdk.item.bonusapply;

import com.qingfeng.cms.domain.bonus.entity.BonusScoreApplyEntity;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/22
 */
@FeignClient(value = "cms-item-manage", fallback = BonusScoreApplyApiFallback.class)
@Component
public interface BonusScoreApplyApi {

    /**
     * 根据加分申请id查询加分信息
     * @param ids
     * @return
     */
    @PostMapping("/bonus_score_apply/score/apply/list")
    public R<List<BonusScoreApplyEntity>> findByIds(@RequestBody List<Long> ids);
}
