package com.qingfeng.sdk.planstandard.module;

import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.currency.base.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

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

    /**
     * 根据模块Id集合查询模块详细信息
     * @param moduleIds
     * @return
     */
    @PostMapping("/creditmodule/info_list")
    public R<List<CreditModuleEntity>> moduleByIds(@RequestBody List<Long> moduleIds);

    /**
     * 根据Id查询学分认定模块信息
     * @param moduleId
     * @return
     */
    @GetMapping("/creditmodule/info/{moduleId}")
    public R info(@PathVariable("moduleId") Long moduleId);
}
