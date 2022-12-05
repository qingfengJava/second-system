package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.rule.service.CreditRulesService;
import com.qingfeng.cms.domain.rule.dto.CreditRulesSaveDTO;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


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

    @ApiOperation(value = "保存学分细则信息", notes = "保存学分细则信息")
    @PostMapping("/save")
    @SysLog("保存学分细则信息")
    public R save(@RequestBody @Validated List<CreditRulesSaveDTO> creditRulesSaveDTOList){
		creditRulesService.saveCreditRules(creditRulesSaveDTOList, getUserId());
        return success();
    }

    @ApiOperation(value = "根据学分id，删除学分和对应的等级信息")
    @DeleteMapping("/delete")
    @SysLog("根据学分id，删除学分和对应的等级信息")
    public R delete(@ApiParam(value = "学分id", required = true)
                    @RequestParam("id") Long id){
        creditRulesService.removeLevelById(id);
        return success();
    }
}
