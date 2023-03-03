package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.bonus.service.BonusCheckService;
import com.qingfeng.cms.domain.bonus.dto.BonusCheckQueryDTO;
import com.qingfeng.cms.domain.bonus.dto.BonusCheckSaveDTO;
import com.qingfeng.cms.domain.bonus.enums.BonusCheckStatusEnum;
import com.qingfeng.cms.domain.bonus.ro.EnumsRo;
import com.qingfeng.cms.domain.bonus.vo.BonusCheckVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * 加分文件审核表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-03 11:28:11
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供加分文件审核的相关功能", tags = "加分文件审核")
@RequestMapping("/bonus_check")
public class BonusCheckController extends BaseController  {

    @Autowired
    private BonusCheckService bonusCheckService;

    @ApiOperation(value = "查询提交了加分文件的活动信息", notes = "查询提交了加分文件的活动信息")
    @PostMapping
    @SysLog("查询提交了加分文件的活动信息")
    public R<BonusCheckVo> bonusList(@RequestBody @Validated BonusCheckQueryDTO bonusCheckQueryDTO){
        BonusCheckVo bonusCheckVo = bonusCheckService.bonusList(bonusCheckQueryDTO);
        return success(bonusCheckVo);
    }

    @ApiOperation(value = "添加加分审核结果", notes = "添加加分审核结果")
    @PutMapping
    @SysLog("添加加分审核结果")
    public R updateBonus(@RequestBody @Validated BonusCheckSaveDTO bonusCheckSaveDTO) throws IOException {
		bonusCheckService.updateBonus(bonusCheckSaveDTO);
        return success();
    }

    @ApiOperation(value = "返回相关的枚举值", notes = "返回相关的枚举值")
    @GetMapping("/anno")
    public R enumsList(){
        return success(Arrays.stream(BonusCheckStatusEnum.values()).map(b -> EnumsRo.builder()
                .label(b.getDesc())
                .value(b.getCode())
                .build())
                .collect(Collectors.toList()));
    }

}
