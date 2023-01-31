package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.apply.service.ApplyCheckService;
import com.qingfeng.cms.domain.apply.entity.ApplyCheckEntity;
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
 * 活动审核表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供活动审核的相关功能", tags = "提供活动审核的相关功能")
@RequestMapping("/apply_check")
public class ApplyCheckController extends BaseController  {

    @Autowired
    private ApplyCheckService applyCheckService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

        return success();
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		ApplyCheckEntity applyCheck = applyCheckService.getById(id);

        return success();
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ApplyCheckEntity applyCheck){
		applyCheckService.save(applyCheck);

        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody ApplyCheckEntity applyCheck){
		applyCheckService.updateById(applyCheck);

        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		applyCheckService.removeByIds(Arrays.asList(ids));

        return success();
    }

}
