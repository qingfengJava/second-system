package com.qingfeng.currency.gateway.api;

import com.qingfeng.currency.authority.dto.auth.ResourceQueryDTO;
import com.qingfeng.currency.authority.entity.auth.Resource;
import com.qingfeng.currency.base.R;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * 资源API熔断
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/18
 */
@Component
public class ResourceApiFallback implements ResourceApi {

    @Override
    public R<List> list() {
        return null;
    }

    @Override
    public R<List<Resource>> visible(ResourceQueryDTO resource) {
        return null;
    }
}