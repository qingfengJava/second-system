package com.qingfeng.sdk.auth.station;

import com.qingfeng.currency.authority.entity.core.Station;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/1/9
 */
@FeignClient(value = "currency-auth-server", fallback = StationApiFallback.class)
@Component
public interface StationApi {

    /**
     * 根据组织id查询岗位
     * @param orgId
     * @return
     */
    @GetMapping("/station/orgId/{orgId}")
    public R<List<Station>> findByOrgId(@PathVariable("orgId") Long orgId);
}
