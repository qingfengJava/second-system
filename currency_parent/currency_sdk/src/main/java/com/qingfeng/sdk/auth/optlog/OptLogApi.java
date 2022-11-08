package com.qingfeng.sdk.auth.optlog;

import com.qingfeng.currency.log.entity.OptLogDTO;
import com.qingfeng.sdk.auth.role.RoleApiFallback;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/11/8
 */
@FeignClient(value = "currency-auth-server", fallback = RoleApiFallback.class)
@Component
public interface OptLogApi {

    /**
     * 保存操作日志
     * @param optLogDTO
     */
    @PostMapping("/optLog")
    public void save(@RequestBody OptLogDTO optLogDTO);
}
