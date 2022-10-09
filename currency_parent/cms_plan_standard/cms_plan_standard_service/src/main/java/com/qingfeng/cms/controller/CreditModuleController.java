package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.module.service.CreditModuleService;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
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
 * 学分认定模块表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供学分认定表模块的相关功能", tags = "学分认定模块")
@RequestMapping("/creditmodule")
public class CreditModuleController extends BaseController {

    @Autowired
    private CreditModuleService creditModuleService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){
        //PageUtils page = creditModuleService.queryPage(params);

        return success();
    }


    /**
     * 信息
     */
    @GetMapping("/info/{moduleId}")
    public R info(@PathVariable("moduleId") Long moduleId){
		CreditModuleEntity creditModule = creditModuleService.getById(moduleId);

        return success(creditModule);
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CreditModuleEntity creditModule){
		creditModuleService.save(creditModule);

        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody CreditModuleEntity creditModule){
		creditModuleService.updateById(creditModule);

        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] moduleIds){
		creditModuleService.removeByIds(Arrays.asList(moduleIds));

        return success();
    }

}
