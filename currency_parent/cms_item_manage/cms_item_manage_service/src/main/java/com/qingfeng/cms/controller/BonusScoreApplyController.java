package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.bonus.service.BonusScoreApplyService;
import com.qingfeng.cms.domain.bonus.dto.BonusScoreApplySaveDTO;
import com.qingfeng.cms.domain.bonus.dto.BonusScoreApplyUpdateDTO;
import com.qingfeng.cms.domain.bonus.vo.BonusScoreApplyVo;
import com.qingfeng.cms.domain.module.entity.CreditModuleEntity;
import com.qingfeng.cms.domain.project.vo.ProjectListVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import com.qingfeng.sdk.planstandard.module.CreditModuleApi;
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

import java.util.List;
import java.util.Map;


/**
 * 加分申报表（提交加分细则申请）
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供的相关功能", tags = "")
@RequestMapping("/bonus_score_apply")
public class BonusScoreApplyController extends BaseController {

    @Autowired
    private BonusScoreApplyService bonusScoreApplyService;
    @Autowired
    private CreditModuleApi creditModuleApi;


    @ApiOperation(value = "分页查询用户申请的加分申报信息", notes = "分页查询用户申请的加分申报信息")
    @PostMapping("/page/list")
    @SysLog("分页查询用户申请的加分申报信息")
    public R list(@RequestParam Map<String, Object> params) {

        return success();
    }

    @ApiOperation(value = "查询当日项目加分申报信息", notes = "查询当日项目加分申报信息")
    @GetMapping("/same/day")
    @SysLog("查询当日项目加分申报信息")
    public R<List<BonusScoreApplyVo>> findBonusScoreSameDay(){
        List<BonusScoreApplyVo> bonusScoreApplyVoList = bonusScoreApplyService.findBonusScoreSameDay(getUserId());
        return success(bonusScoreApplyVoList);
    }

    @ApiOperation(value = "项目加分申报", notes = "项目加分申报")
    @PostMapping("/save")
    public R save(@ApiParam(value = "加分申报实体")
                  @RequestBody BonusScoreApplySaveDTO bonusScoreApplySaveDTO) {
        bonusScoreApplyService.saveBonusScoreApply(bonusScoreApplySaveDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "重新上传证明材料", notes = "重新上传证明材料")
    @PutMapping("/update")
    @SysLog("重新上传证明材料")
    public R update(@RequestBody @Validated BonusScoreApplyUpdateDTO bonusScoreApplyUpdateDTO) {
        bonusScoreApplyService.updateBonusScoreApply(bonusScoreApplyUpdateDTO);
        return success();
    }

    @ApiOperation(value = "取消项目的加分申报", notes = "取消项目的加分申报")
    @DeleteMapping("/delete/{id}")
    @SysLog("取消项目的加分申报")
    public R delete(@PathVariable("id") Long id) {
        bonusScoreApplyService.cancelBonusById(id);
        return success();
    }

    @ApiOperation(value = "查询学生下的所属方案的方案模块", notes = "查询学生下的所属方案的方案模块")
    @GetMapping("/module")
    @SysLog("查询学生下的所属方案的方案模块")
    public R<List<CreditModuleEntity>> moduleList() {
        return success(creditModuleApi.moduleListByStuId().getData());
    }

    @ApiOperation(value = "根据模块Id查询项目等级学分信息", notes = "根据模块Id查询项目等级学分信息")
    @GetMapping("/project/{moduleId}")
    @SysLog("根据模块Id查询项目等级学分信息")
    public R<List<ProjectListVo>> projectList(@PathVariable("moduleId") Long moduleId) {
        List<ProjectListVo> projectVoList = bonusScoreApplyService.projectList(moduleId, getUserId());
        return success(projectVoList);
    }
}
