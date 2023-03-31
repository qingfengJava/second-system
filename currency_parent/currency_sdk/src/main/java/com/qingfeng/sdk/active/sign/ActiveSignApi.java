package com.qingfeng.sdk.active.sign;

import com.qingfeng.cms.domain.sign.entity.ActiveSignEntity;
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
@FeignClient(value = "cms-community-activities", fallback = ActiveSignApiFallback.class)
@Component
public interface ActiveSignApi {

    /**
     * 根据Id集合查询学生报名的活动信息
     * @param applyIds
     * @return
     */
    @PostMapping("/active_sign/applyIds/list")
    public R<List<ActiveSignEntity>> findByApplyIdsList(@RequestBody List<Long> applyIds);
}
