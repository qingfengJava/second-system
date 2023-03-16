package com.qingfeng.sdk.planstandard.module;

import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.plan.ro.PlanTreeRo;
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
    public R<CreditModuleEntity> info(@PathVariable("moduleId") Long moduleId);

    /**
     * 查询所有方案和模块内容，并分组排序
     * @return
     */
    @GetMapping("/creditmodule/findPlanAndModule")
    public R<List<PlanTreeRo>> findPlanAndModule();

    /**
     * 根据方案Id查询模块信息
     * @param planId
     * @return
     */
    @GetMapping("/creditmodule/module/{planId}")
    public R<List<CreditModuleEntity>> findModuleListByPlanId(@PathVariable("planId") Long planId);
}
