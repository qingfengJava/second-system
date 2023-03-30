package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.apply.service.ApplyService;
import com.qingfeng.cms.domain.apply.dto.ApplyCheckQueryDTO;
import com.qingfeng.cms.domain.apply.dto.ApplyQueryDTO;
import com.qingfeng.cms.domain.apply.dto.ApplySaveDTO;
import com.qingfeng.cms.domain.apply.dto.ApplyUpdateDTO;
import com.qingfeng.cms.domain.apply.entity.ApplyEntity;
import com.qingfeng.cms.domain.apply.enums.ActiveLevelEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveScaleEnum;
import com.qingfeng.cms.domain.apply.enums.ActiveStatusEnum;
import com.qingfeng.cms.domain.apply.enums.AgreeStatusEnum;
import com.qingfeng.cms.domain.apply.enums.IsBonusPointsApplyEnum;
import com.qingfeng.cms.domain.apply.enums.IsQuotaEnum;
import com.qingfeng.cms.domain.apply.enums.IsReleaseEnum;
import com.qingfeng.cms.domain.apply.ro.ActiveApplyCheckRo;
import com.qingfeng.cms.domain.apply.ro.ActiveReleaseRo;
import com.qingfeng.cms.domain.apply.ro.EnumsRo;
import com.qingfeng.cms.domain.apply.vo.ApplyCheckListVo;
import com.qingfeng.cms.domain.apply.vo.ApplyEnumsVoList;
import com.qingfeng.cms.domain.apply.vo.ApplyListVo;
import com.qingfeng.cms.domain.apply.vo.BonusFileVo;
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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Arrays;
import java.util.List;
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

    @ApiOperation(value = "活动申请查询列表", notes = "活动申请查询列表")
    @PostMapping("/list")
    @SysLog("活动申请查询列表")
    public R<ApplyListVo> list(@RequestBody @Validated ApplyQueryDTO applyQueryDTO) {
        return success(applyService.findApplyList(applyQueryDTO, getUserId()));
    }

    @ApiOperation(value = "活动申请审核列表", notes = "活动申请审核列表")
    @PostMapping("/list/check")
    @SysLog("活动申请审核列表")
    public R<ApplyCheckListVo> applyCheckList(@RequestBody @Validated ApplyCheckQueryDTO applyCheckQueryDTO) {
        return success(applyService.findApplyCheckList(applyCheckQueryDTO));
    }

    @ApiOperation(value = "根据Id查询活动申请的详细信息", notes = "根据Id查询活动申请的详细信息")
    @GetMapping("/info/{id}")
    @SysLog("根据Id查询活动申请的详细信息")
    public R<ApplyEntity> info(@ApiParam(value = "活动申请Id", required = true)
                               @PathVariable("id") @NotNull Long id) {
        return success(applyService.getById(id));
    }

    @ApiOperation(value = "根据Ids查询活动集合", notes = "根据Ids查询活动集合")
    @PostMapping("/info_list")
    @SysLog("根据Ids查询活动集合")
    public R<List<ApplyEntity>> infoListByIds(@RequestBody List<Long> ids) {
        return success(applyService.list(Wraps.lbQ(new ApplyEntity())
                        .in(ApplyEntity::getId, ids)
                )
        );
    }

    @ApiOperation(value = "活动申请信息保存", notes = "活动申请信息保存")
    @PostMapping("/save")
    @SysLog("活动申请信息保存")
    public R save(@RequestBody @Validated ApplySaveDTO applySaveDTO) {
        applyService.saveApply(applySaveDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "活动申请信息修改", notes = "活动申请信息修改")
    @PutMapping("/update")
    @SysLog("活动申请信息修改")
    public R update(@RequestBody @Validated(SuperEntity.Update.class) ApplyUpdateDTO applyUpdateDTO) {
        applyService.updateApplyById(applyUpdateDTO);
        return success();
    }

    @ApiOperation(value = "撤销活动申请（删除）", notes = "撤销活动申请（删除）")
    @DeleteMapping
    @SysLog("撤销活动申请")
    public R delete(@RequestParam("id") Long id) {
        applyService.removeActiveById(id);
        return success();
    }

    @ApiOperation(value = "活动申请审核", notes = "活动申请审核")
    @PostMapping("/apply/check")
    @SysLog("活动申请审核")
    public R activeApplyCheck(@RequestBody @Validated(SuperEntity.Update.class) ActiveApplyCheckRo activeApplyCheckRo) {
        applyService.activeApplyCheck(activeApplyCheckRo, getUserId());
        return success();
    }

    @ApiOperation(value = "活动一键发布", notes = "活动一键发布")
    @PostMapping("/release")
    @SysLog("活动一键发布")
    public R activeRelease(@RequestBody @Validated(SuperEntity.Update.class) ActiveReleaseRo activeReleaseRo) {
        applyService.activeRelease(activeReleaseRo);
        return success();
    }

    @ApiOperation(value = "提交加分文件", notes = "提交加分文件")
    @PostMapping("/bonus/file")
    @SysLog("提交加分文件")
    public R uploadBonusFile(@RequestBody @Validated BonusFileVo bonusFileVo) {
        applyService.uploadBonusFile(bonusFileVo);
        return success();
    }

    @ApiOperation(value = "根据用户Id查询用户申请的活动信息", notes = "根据用户Id查询用户申请的活动信息")
    @GetMapping("/user/{userId}")
    @SysLog("根据用户Id查询用户申请的活动信息")
    public R<List<ApplyEntity>> getUserActivityList(@PathVariable("userId") Long userId) {
        return success(
                applyService.list(
                        Wraps.lbQ(new ApplyEntity())
                                .eq(ApplyEntity::getApplyUserId, userId)
                )
        );
    }

    @ApiOperation(value = "返回相关的枚举值", notes = "返回相关的枚举值")
    @GetMapping("/anno/enums")
    public R<ApplyEnumsVoList> getApplyEnumsVoList() {
        return success(ApplyEnumsVoList.builder()
                .activeScale(Arrays.stream(ActiveScaleEnum.values())
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
