package com.qingfeng.sdk.auth.role;

import com.qingfeng.currency.authority.entity.auth.vo.UserRoleVo;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/1/9
 */
@FeignClient(value = "currency-auth-server", fallback = UserRoleApiFallback.class)
@Component
public interface UserRoleApi {

    /**
     * 根据用户Id查询角色Id及编码
     * @param userId
     * @return
     */
    @GetMapping("/user_role/findByUserI/{userId}")
    public R<UserRoleVo> findRoleIdByUserId(@PathVariable("userId") Long userId);
}
