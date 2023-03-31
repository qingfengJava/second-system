package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.apply.service.ApplyCheckService;
import com.qingfeng.cms.domain.apply.dto.ActiveCheckQueryDTO;
import com.qingfeng.cms.domain.apply.dto.ApplyCheckDTO;
import com.qingfeng.cms.domain.apply.dto.ApplyCheckSaveDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyCheckEntity;
import com.qingfeng.cms.domain.apply.enums.ApplyCheckStatusEnum;
import com.qingfeng.cms.domain.apply.ro.EnumsRo;
import com.qingfeng.cms.domain.apply.vo.ApplyCheckVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


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
public class ApplyCheckController extends BaseController {

    @Autowired
    private ApplyCheckService applyCheckService;

    @ApiOperation(value = "查询待终审的活动", notes = "查询待终审的活动")
    @PostMapping("/list")
    @SysLog("查询待终审的活动")
    public R<ApplyCheckVo> applyCheckList(@RequestBody @Validated ActiveCheckQueryDTO activeCheckQueryDTO) {
        ApplyCheckVo applyCheckVo = applyCheckService.applyCheckList(activeCheckQueryDTO);
        return success(applyCheckVo);
    }

    @ApiOperation(value = "提交活动终审资料", notes = "提交活动终审资料")
    @PostMapping("/save")
    @SysLog("提交活动终审资料")
    public R saveApplyCheck(@RequestBody @Validated ApplyCheckSaveDTO applyCheckSaveDTO) {
        applyCheckService.saveApplyCheck(applyCheckSaveDTO);
        return success();
    }

    @ApiOperation(value = "根据活动Id查询提交的审核资料", notes = "根据活动Id查询提交的审核资料")
    @GetMapping("/{applyId}")
    @SysLog("根据活动Id查询提交的审核资料")
    public R<ApplyCheckEntity> applyCheckInfo(@PathVariable("applyId") Long applyId) {
        return success(applyCheckService.getOne(
                        Wraps.lbQ(new ApplyCheckEntity())
                                .eq(ApplyCheckEntity::getApplyId, applyId)
                )
        );
    }

    @ApiOperation(value = "提交活动终审结果")
    @PostMapping("/last/instance")
    @SysLog("提交活动终审结果")
    public R submitApplyCheck(@RequestBody @Validated ApplyCheckDTO applyCheckDTO) {
        applyCheckService.submitApplyCheck(applyCheckDTO);
        return success();
    }

    @ApiOperation(value = "根据活动Id查询所有已经终审的活动信息", notes = "根据活动Id查询所有已经终审的活动信息")
    @PostMapping("/ids/list")
    @SysLog("根据活动Id查询所有已经终审的活动信息")
    public R<List<ApplyCheckEntity>> findByApplyIds(@RequestBody List<Long> applyIds) {
        return success(applyCheckService.list(Wraps.lbQ(new ApplyCheckEntity())
                .in(ApplyCheckEntity::getApplyId, applyIds)));
    }

    @ApiOperation(value = "返回活动终审枚举")
    @GetMapping("/anno")
    public R enumsList() {
        return success(Arrays.stream(ApplyCheckStatusEnum.values())
                .map(
                        a -> EnumsRo.builder()
                                .label(a.getDesc())
                                .value(a.getCode())
                                .build()
                )
                .collect(Collectors.toList())
        );
    }
}
