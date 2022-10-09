package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.plan.service.PlanService;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;

/**
 * 方案设定表（是否是修读标准，本科标准，专科标准）
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
@RestController
@Slf4j
@Validated
@Api(value = "提供方案设定相关的接口功能", tags = "方案设定模块")
@RequestMapping("/plans")
public class PlanController extends BaseController {

    @Autowired
    private PlanService planService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        //PageUtils page = planService.queryPage(params);

        return success();
    }


    /**
     * 信息
     */
    @GetMapping("/info/{planId}")
    public R info(@PathVariable("planId") Long planId){
		PlanEntity plan = planService.getById(planId);

        return success(plan);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody PlanEntity plan){
		planService.save(plan);

        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody PlanEntity plan){
		planService.updateById(plan);

        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] planIds){
		planService.removeByIds(Arrays.asList(planIds));

        return success();
    }

}
