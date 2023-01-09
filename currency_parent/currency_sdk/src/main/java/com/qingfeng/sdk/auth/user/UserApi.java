package com.qingfeng.sdk.auth.user;

import com.qingfeng.currency.authority.entity.auth.User;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/12
 */
@FeignClient(value = "currency-auth-server", fallback = UserApiFallback.class)
@Component
public interface UserApi {

    /**
     * 根据id查询用户信息
     * @param id
     * @return
     */
    @GetMapping("/user/{id}")
    public R<User> get(@PathVariable Long id);

    /**
     * 根据组织和岗位Id查询用户信息
     * @param orgId
     * @param stationId
     * @return
     */
    @GetMapping("/user/{orgId}/{stationId}")
    public R<User> getByOrgIdAndStationId(@PathVariable("orgId") Long orgId,
                                          @PathVariable("stationId") Long stationId);
}
