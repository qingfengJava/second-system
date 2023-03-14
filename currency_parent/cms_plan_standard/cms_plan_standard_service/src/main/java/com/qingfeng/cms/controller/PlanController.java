package com.qingfeng.cms.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qingfeng.cms.biz.plan.service.PlanService;
import com.qingfeng.cms.domain.plan.dto.PlanPageDTO;
import com.qingfeng.cms.domain.plan.dto.PlanSaveDTO;
import com.qingfeng.cms.domain.plan.dto.PlanUpdateDTO;
import com.qingfeng.cms.domain.plan.entity.PlanEntity;
import com.qingfeng.cms.domain.plan.vo.PlanEntityVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.base.entity.SuperEntity;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
import com.qingfeng.currency.dozer.DozerUtils;
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
import java.util.List;

/**
 * 方案设定表（是否是修读标准，本科标准，专科标准）
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-10-08 19:44:16
 */
@RestController
@Slf4j
@Validated
@Api(value = "提供方案设定相关的接口功能", tags = "方案设定模块")
@RequestMapping("/plans")
public class PlanController extends BaseController {

    @Autowired
    private PlanService planService;
    @Autowired
    private DozerUtils dozer;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "10"),
    })
    @ApiOperation(value = "分页查询学分修读方案", notes = "分页查询学分修读方案")
    @GetMapping("/page")
    @SysLog("分页查询学分修读方案")
    public R<IPage<PlanEntity>> page(PlanPageDTO data) {
        IPage<PlanEntity> page = getPage();

        PlanEntity planEntity = dozer.map(data, PlanEntity.class);
        planEntity.setParentId(0L);

        // 构建值不为null的查询条件
        LbqWrapper<PlanEntity> query = Wraps.lbQ(planEntity)
                .orderByDesc(PlanEntity::getYear)
                .orderByDesc(PlanEntity::getGrade);
        planService.page(page, query);

        // 查询的时候是否增加父子类关系
        page.getRecords().forEach(p -> {
            QueryWrapper<PlanEntity> wrapper = new QueryWrapper<PlanEntity>()
                    .eq("parent_id", p.getId());
            p.setChildren(planService.list(wrapper));
        });

        return success(page);
    }

    @ApiOperation(value = "保存方案信息", notes = "保存方案信息")
    @PostMapping("/save")
    @SysLog("新增保存方案")
    public R<PlanEntity> save(@ApiParam(value = "方案实体", required = true)
                              @RequestBody @Validated PlanSaveDTO planDto) {
        PlanEntity plan = dozer.map(planDto, PlanEntity.class);
        planService.savePlan(plan);

        return success(plan);
    }

    @ApiOperation(value = "根据Id查询方案信息", notes = "根据Id查询方案信息")
    @GetMapping("/info/{planId}")
    @SysLog("根据Id查询方案信息")
    public R<PlanEntityVo> info(@ApiParam(value = "方案Id", required = true)
                              @PathVariable("planId") @NotNull Long planId) {
        PlanEntityVo planEntityVo = planService.getPlanInfo(planId);
        return success(planEntityVo);
    }

    @ApiOperation(value = "修改学分修读方案", notes = "修改学分修读方案字段不能为空")
    @PutMapping("/update")
    @SysLog("修改学分修读方案")
    public R<PlanEntity> update(@ApiParam("学分修读方案实体")
                                @RequestBody @Validated(SuperEntity.Update.class) PlanUpdateDTO planDto) {
        PlanEntity plan = dozer.map(planDto, PlanEntity.class);
        planService.updatePlan(plan);
        return success(plan);
    }

    @ApiOperation(value = "删除学分修读方案", notes = "删除学分修读方案id不能为空")
    @DeleteMapping
    @SysLog("删除学分修读方案")
    public R<Boolean> delete(@ApiParam(value = "方案Id集合", required = true)
                             @RequestParam("ids[]") List<Long> ids) {
        planService.removeByIds(ids);

        return success(true);
    }

    @ApiOperation(value = "根据学生用户Id，查询方案方针", notes = "根据学生用户Id，查询方案方针")
    @GetMapping
    @SysLog("根据学生用户Id，查询方案方针")
    public R<PlanEntityVo> getPlan() {
        PlanEntityVo planEntityVo = planService.getPlan(getUserId());
        return success(planEntityVo);
    }

    @ApiOperation(value = "根据学生用户Id查询方案信息", notes = "根据学生用户Id查询方案信息")
    @GetMapping("/{userId}")
    @SysLog("根据学生用户Id查询方案信息")
    public R<PlanEntity> getPlanByUserId(@PathVariable("userId") Long userId){
        PlanEntity planEntity = planService.getPlanByUserId(userId);
        return success(planEntity);
    }

}
