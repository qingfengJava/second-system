package com.qingfeng.cms.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.qingfeng.cms.biz.rule.service.CreditRulesService;
import com.qingfeng.cms.domain.rule.dto.CreditRulesCheckDTO;
import com.qingfeng.cms.domain.rule.dto.CreditRulesSaveDTO;
import com.qingfeng.cms.domain.rule.entity.CreditRulesEntity;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.base.entity.SuperEntity;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
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
    public R save(@RequestBody @Validated List<CreditRulesSaveDTO> creditRulesSaveDTOList) {
        creditRulesService.saveCreditRules(creditRulesSaveDTOList, getUserId());
        return success();
    }

    @ApiOperation(value = "根据学分id，删除学分和对应的等级信息")
    @DeleteMapping("/delete")
    @SysLog("根据学分id，删除学分和对应的等级信息")
    public R delete(@ApiParam(value = "学分id", required = true)
                    @RequestParam("id") Long id) {
        creditRulesService.removeLevelById(id);
        return success();
    }

    @ApiOperation(value = "学分细则审核", notes = "学分细则审核")
    @PostMapping("/check")
    @SysLog("学分细则审核")
    public R checkRules(@ApiParam(value = "学分细则审核实体", required = true)
                        @RequestBody @Validated(SuperEntity.Update.class) CreditRulesCheckDTO creditRulesCheckDTO) throws JsonProcessingException {
        creditRulesService.checkRule(creditRulesCheckDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "根据学分细则Id集合查询学分细则信息", notes = "根据学分细则Id查询学分细则信息")
    @PostMapping("/info/list")
    public R<List<CreditRulesEntity>> ruleInfoByIds(@RequestBody List<Long> rulesIds) {
        return success(creditRulesService.list(
                        Wraps.lbQ(new CreditRulesEntity())
                                .in(CreditRulesEntity::getId, rulesIds)
                )
        );
    }
}
