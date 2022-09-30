package com.qingfeng.currency.user.feign;

import com.qingfeng.currency.base.R;
import com.qingfeng.currency.user.feign.fallback.UserResolveApiFallback;
import com.qingfeng.currency.user.model.SysUser;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 用户操作API
 * @author 清风学Java
 */
@FeignClient(name = "${currency.feign.authority-server:currency-auth-server}", fallbackFactory = UserResolveApiFallback.class)
public interface UserResolveApi {
    /**
     * 根据id 查询用户详情
     */
    @PostMapping(value = "/user/anno/id/{id}")
    R<SysUser> getById(@PathVariable("id") Long id, @RequestBody UserQuery userQuery);
}