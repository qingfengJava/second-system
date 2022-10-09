package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.rule.service.CreditRulesService;
import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;
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
 * 加分（学分）细则表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:15
 */
@Slf4j
@Validated
@Api(value = "提供加分细则相关的接口功能", tags = "加分细则模块")
@RestController
@RequestMapping("/creditrules")
public class CreditRulesController extends BaseController {

    @Autowired
    private CreditRulesService creditRulesService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        //PageUtils page = creditRulesService.queryPage(params);

        return success();
    }


    /**
     * 信息
     */
    @GetMapping("/info/{creditRulesId}")
    public R info(@PathVariable("creditRulesId") Long creditRulesId){
		CreditRulesEntity creditRules = creditRulesService.getById(creditRulesId);

        return success(creditRules);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CreditRulesEntity creditRules){
		creditRulesService.save(creditRules);

        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody CreditRulesEntity creditRules){
		creditRulesService.updateById(creditRules);

        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] creditRulesIds){
		creditRulesService.removeByIds(Arrays.asList(creditRulesIds));

        return null;
    }

}
