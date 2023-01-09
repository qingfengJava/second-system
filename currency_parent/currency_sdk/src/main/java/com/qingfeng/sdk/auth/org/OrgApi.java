package com.qingfeng.sdk.auth.org;

import com.qingfeng.currency.authority.entity.core.Org;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/1/9
 */
@FeignClient(value = "currency-auth-server", fallback = OrgApiFallback.class)
@Component
public interface OrgApi {

    /**
     * 根据数据字典的院系名称查询组织中对应的院系信息
     * @param dictName
     * @return
     */
    @GetMapping("/org/dict_name")
    public R<Org> getOrgByDictName(@RequestParam("dictName") String dictName);
}
