package com.qingfeng.sdk.planstandard.module;

import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/3/7
 */
@FeignClient(value = "cms-plan-standard", fallback = CreditModuleApiFallback.class)
@Component
public interface CreditModuleApi {

    /**
     * 查询学生下的方案模块
     * @return
     */
    @GetMapping("/creditmodule/stuId")
    public R<List<CreditModuleEntity>> moduleListByStuId();
}
