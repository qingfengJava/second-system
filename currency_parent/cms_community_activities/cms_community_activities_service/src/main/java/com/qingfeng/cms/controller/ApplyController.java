package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.apply.service.ApplyService;
import com.qingfeng.cms.domain.apply.dto.ApplySaveDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.apply.enums.ActiveLevelEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveScaleEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveStatusEnum;
import com.qingfeng.cms.domain.apply.enums.AgreeStatusEnum;
import com.qingfeng.cms.domain.apply.enums.IsBonusPointsApplyEnum;
import com.qingfeng.cms.domain.apply.enums.IsQuotaEnum;
import com.qingfeng.cms.domain.apply.enums.IsReleaseEnum;
import com.qingfeng.cms.domain.apply.ro.EnumsRo;
import com.qingfeng.cms.domain.apply.vo.ApplyEnumsVoList;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.stream.Collectors;


/**
 * 社团活动申请表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供社团活动申请的相关功能", tags = "提供社团活动申请的相关功能")
@RequestMapping("/apply")
public class ApplyController extends BaseController {

    @Autowired
    private ApplyService applyService;

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list() {

        return success();
    }


    @ApiOperation(value = "根据Id查询活动申请的详细信息", notes = "根据Id查询活动申请的详细信息")
    @GetMapping("/info/{id}")
    @SysLog("根据Id查询活动申请的详细信息")
    public R<ApplyEntity> info(@ApiParam(value = "活动申请Id")
                               @PathVariable("id") Long id) {

        return success(applyService.getById(id));
    }

    @ApiOperation(value = "活动申请信息保存", notes = "活动申请信息保存")
    @PostMapping("/save")
    @SysLog("活动申请信息保存")
    public R save(@RequestBody @Validated ApplySaveDTO applySaveDTO) {
        applyService.saveApply(applySaveDTO);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody ApplyEntity apply) {
        applyService.updateById(apply);

        return success();
    }

    @ApiOperation(value = "撤销活动申请（删除）", notes = "撤销活动申请（删除）")
    @DeleteMapping
    @SysLog("撤销活动申请")
    public R delete(@RequestParam("id") Long id) {
        applyService.removeById(id);
        return success();
    }

    @ApiOperation(value = "返回相关的枚举值", notes = "返回相关的枚举值")
    @GetMapping("/anno/enums")
    public R<ApplyEnumsVoList> getApplyEnumsVoList() {
        return success(ApplyEnumsVoList.builder()
                .activeLevel(Arrays.stream(ActiveScaleEnum.values())
                        .map(a -> buildEnum(a.name(), a.getDesc()))
                        .collect(Collectors.toList()))
                .activeLevel(Arrays.stream(ActiveLevelEnum.values())
                        .map(a -> buildEnum(a.name(), a.getDesc()))
                        .collect(Collectors.toList()))
                .isQuota(Arrays.stream(IsQuotaEnum.values())
                        .map(q -> buildEnum(q.name(), q.getDesc()))
                        .collect(Collectors.toList()))
                .isBonusPointsApply(Arrays.stream(IsBonusPointsApplyEnum.values())
                        .map(b -> buildEnum(b.name(), b.getDesc()))
                        .collect(Collectors.toList()))
                .agreeStatus(Arrays.stream(AgreeStatusEnum.values())
                        .map(a -> buildEnum(a.name(), a.getDesc()))
                        .collect(Collectors.toList()))
                .activeStatus(Arrays.stream(ActiveStatusEnum.values())
                        .map(a -> buildEnum(a.name(), a.getDesc()))
                        .collect(Collectors.toList()))
                .isRelease(Arrays.stream(IsReleaseEnum.values())
                        .map(r -> buildEnum(r.name(), r.getDesc()))
                        .collect(Collectors.toList()))
                .build());
    }

    private EnumsRo buildEnum(String name, String desc) {
        return EnumsRo.builder()
                .value(name)
                .label(desc)
                .build();
    }

}
