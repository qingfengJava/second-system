package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.apply.service.ApplyCheckService;
import com.qingfeng.cms.domain.apply.dto.ActiveCheckQueryDTO;
import com.qingfeng.cms.domain.apply.dto.ApplyCheckSaveDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyCheckEntity;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;



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

    @ApiOperation(value = "查询待终审的活动" , notes = "查询待终审的活动")
    @PostMapping("/list")
    @SysLog("查询待终审的活动")
    public R applyCheckList(@RequestBody ActiveCheckQueryDTO activeCheckQueryDTO){

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

    @ApiOperation(value = "提交活动终审资料", notes = "提交活动终审资料")
    @PostMapping("/save")
    @SysLog("提交活动终审资料")
    public R saveApplyCheck(@RequestBody @Validated ApplyCheckSaveDTO applyCheckSaveDTO){
		applyCheckService.saveApplyCheck(applyCheckSaveDTO);
        return success();
    }


}
