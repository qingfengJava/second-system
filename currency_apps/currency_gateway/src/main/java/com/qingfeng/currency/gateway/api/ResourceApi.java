package com.qingfeng.currency.gateway.api;

import com.qingfeng.currency.authority.dto.auth.ResourceQueryDTO;
import com.qingfeng.currency.authority.entity.auth.Resource;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/18
 */
@FeignClient(value = "currency-auth-server", fallback = ResourceApiFallback.class)
@Component
public interface ResourceApi {

    /**
     * 获取所有需要鉴权的资源
     * @return
     */
    @GetMapping("/resource/list")
    public R<List> list();

    /**
     * 查询当前登录用户拥有的资源权限
     * @param resource
     * @return
     */
    @GetMapping("/resource")
    public R<List<Resource>> visible(ResourceQueryDTO resource);
}
