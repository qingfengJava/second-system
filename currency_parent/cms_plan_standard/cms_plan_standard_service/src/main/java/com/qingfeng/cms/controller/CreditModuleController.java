package com.qingfeng.cms.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qingfeng.cms.biz.module.service.CreditModuleService;
import com.qingfeng.cms.domain.module.dto.CreditModuleQueryDTO;
import com.qingfeng.cms.domain.module.dto.CreditModuleSaveDTO;
import com.qingfeng.cms.domain.module.dto.CreditModuleUpdateDTO;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.module.enums.CreditModuleTypeEnum;
import com.qingfeng.cms.domain.module.ro.EnumsRo;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import com.qingfeng.cms.domain.plan.ro.PlanTreeRo;
import com.qingfeng.cms.domain.plan.vo.PlanVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.base.entity.SuperEntity;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
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

    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @ApiOperation(value = "分页查询学分认定模块列表", notes = "分页查询学分认定模块列表")
    @GetMapping("/list")
    @SysLog("分页查询学分认定模块列表")
    public R<IPage<PlanVo>> list(CreditModuleQueryDTO creditModuleQueryDTO) {
        //分页查询，首先还是要分页查询启用的修读方案
        IPage<PlanEntity> page = getPage();

        IPage<PlanVo> iPage = creditModuleService.findList(page, creditModuleQueryDTO);

        return success(iPage);
    }

    @ApiOperation(value = "查询所有方案和模块内容，并分组排序", notes = "查询所有方案和模块内容，并分组排序")
    @GetMapping("/findPlanAndModule")
    @SysLog("分页查询学分认定模块列表")
    public R<List<PlanTreeRo>> findPlanAndModule() {
        List<PlanTreeRo> planTreeRoList = creditModuleService.findPlanAndModule();
        return success(planTreeRoList);
    }


    @ApiOperation(value = "根据Id查询学分认定模块信息", notes = "根据Id查询学分认定模块信息")
    @GetMapping("/info/{moduleId}")
    @SysLog("根据Id查询学分认定模块信息")
    public R<CreditModuleEntity> info(@ApiParam(value = "学分认定模块Id", required = true)
                  @PathVariable("moduleId") @NotNull Long moduleId) {
        CreditModuleEntity creditModule = creditModuleService.getById(moduleId);
        return success(creditModule);
    }

    @ApiOperation(value = "保存学分认定模块信息", notes = "保存学分认定模块信息")
    @PostMapping("/save")
    @SysLog("保存学分认定模块信息")
    public R save(@ApiParam(value = "学分认定模块实体", required = true)
                  @RequestBody @Validated CreditModuleSaveDTO creditModuleSaveDTO) {
        creditModuleService.saveCreditModule(creditModuleSaveDTO);
        return success();
    }

    @ApiOperation(value = "修改学分认定模块的信息", notes = "修改学分认定模块的信息")
    @PutMapping("/update")
    @SysLog("修改学分认定模块的信息")
    public R update(@ApiParam(value = "学分认定模块实体")
                    @RequestBody @Validated(SuperEntity.Update.class) CreditModuleUpdateDTO creditModuleUpdateDTO) {
        creditModuleService.updateCreditModuleById(creditModuleUpdateDTO);
        return success();
    }

    @ApiOperation(value = "根据Id删除学分认定模块", notes = "根据Id删除学分认定模块")
    @DeleteMapping
    @SysLog("删除学分认定模块")
    public R delete(@ApiParam(value = "学分认定模块Id", required = true)
                    @RequestParam("ids[]") List<Long> ids) {
        creditModuleService.deleteByIds(ids);
        return success();
    }

    @ApiOperation(value = "查询学生下的方案模块", notes = "查询学生下的方案模块")
    @GetMapping("/stuId")
    @SysLog("查询学生下的方案模块")
    public R<List<CreditModuleEntity>> moduleListByStuId() {
        List<CreditModuleEntity> moduleList = creditModuleService.moduleListByStuId(getUserId());
        return success(moduleList);
    }

    @ApiOperation(value = "返回模块类型枚举", notes = "返回模块类型枚举")
    @GetMapping("/anno/module/enum")
    public R<List<EnumsRo>> moduleEnum() {
        return success(Arrays.stream(CreditModuleTypeEnum.values())
                .map(m -> EnumsRo.builder()
                        .label(m.getCode() + "(" + m.getDesc() + ")")
                        .value(m.getCode())
                        .build())
                .collect(Collectors.toList()));
    }

    @ApiOperation(value = "根据模块Id集合查询模块详细信息", notes = "根据模块Id集合查询模块详细信息")
    @PostMapping("/info_list")
    public R<List<CreditModuleEntity>> moduleByIds(@RequestBody List<Long> moduleIds) {
        return success(creditModuleService.list(Wraps.lbQ(new CreditModuleEntity())
                        .in(CreditModuleEntity::getId, moduleIds)
                )
        );
    }

    @ApiOperation(value = "根据方案Id查询模块信息", notes = "根据方案Id查询模块信息")
    @GetMapping("/module/{planId}")
    public R<List<CreditModuleEntity>> findModuleListByPlanId(@PathVariable("planId") Long planId) {
        return success(creditModuleService.list(
                        Wraps.lbQ(new CreditModuleEntity())
                                .eq(CreditModuleEntity::getPlanId, planId)
                )
        );
    }

}
