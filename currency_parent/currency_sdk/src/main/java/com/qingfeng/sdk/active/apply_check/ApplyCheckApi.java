package com.qingfeng.sdk.active.apply_check;

import com.qingfeng.cms.domain.apply.entity.ApplyCheckEntity;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/31
 */
@FeignClient(value = "cms-community-activities", fallback = ApplyCheckApiFallback.class)
@Component
public interface ApplyCheckApi {

    /**
     * 根据活动Id查询所有已经终审的活动信息
     * @param applyIds
     * @return
     */
    @PostMapping("/apply_check/ids/list")
    public R<List<ApplyCheckEntity>> findByApplyIds(@RequestBody List<Long> applyIds);
}
