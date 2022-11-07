package com.qingfeng.sdk.auth.role;

import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/7
 */
@FeignClient(value = "currency-auth-server", fallback = RoleApiFallback.class)
@Component
public interface RoleApi {

    /**
     * 根据角色编码查询用户ID
     * @param codes
     * @return
     */
    @GetMapping("/role/codes")
    public R<List<Long>> findUserIdByCode(@RequestParam(value = "codes") String[] codes);
}
