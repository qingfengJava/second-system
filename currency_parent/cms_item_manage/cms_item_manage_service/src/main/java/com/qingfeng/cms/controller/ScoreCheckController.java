package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.check.service.ScoreCheckService;
import com.qingfeng.cms.domain.check.dto.BonusScoreApplyCheckPageDTO;
import com.qingfeng.cms.domain.check.dto.ScoreCheckSaveDTO;
import com.qingfeng.cms.domain.check.dto.ScoreCheckUpdateDTO;
import com.qingfeng.cms.domain.check.enums.CheckStatusEnums;
import com.qingfeng.cms.domain.check.ro.EnumsRo;
import com.qingfeng.cms.domain.check.vo.BonusScoreCheckPageVo;
import com.qingfeng.cms.domain.check.vo.PlanModuleVo;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 加分审核表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供加分审核表的相关功能", tags = "提供加分审核表的相关功能")
@RequestMapping("/score_check")
public class ScoreCheckController extends BaseController {

    @Autowired
    private ScoreCheckService scoreCheckService;

    @ApiOperation(value = "分页查询对应用户需要审核的加分需求", notes = "分页查询对应用户需要审核的加分需求")
    @PostMapping("/list")
    @SysLog("分页查询对应用户需要审核的加分需求")
    public R<BonusScoreCheckPageVo> listBonusScore(@RequestBody @Validated BonusScoreApplyCheckPageDTO bonusScoreApplyPageDTO) {
        BonusScoreCheckPageVo bonusScoreCheckPageVo = scoreCheckService.listBonusScore(bonusScoreApplyPageDTO, getUserId());
        return success(bonusScoreCheckPageVo);
    }

    @ApiOperation(value = "查询方案下的模块信息", notes = "查询方案下的模块信息")
    @GetMapping("/module/list")
    @SysLog("查询方案下的模块信息")
    public R<List<PlanModuleVo>> moduleList(){
        List<PlanModuleVo> planModuleVoList = scoreCheckService.findPlanModuleList(getUserId());
        return success(planModuleVoList);
    }

    @ApiOperation(value = "项目加分申请审核", notes = "项目加分申请审核")
    @PostMapping("/save/check")
    @SysLog("项目加分申请审核")
    public R saveCheck(@RequestBody @Validated ScoreCheckSaveDTO scoreCheckSaveDTO){
        scoreCheckService.saveCheck(scoreCheckSaveDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "误判，取消项目申请加分", notes = "误判、取消项目申请加分")
    @PutMapping
    @SysLog("误判，取消项目申请加分")
    public R updateBonusPoints(@RequestBody @Validated ScoreCheckUpdateDTO scoreCheckUpdateDTO){
        scoreCheckService.updateBonusPoints(scoreCheckUpdateDTO);
        return success();
    }


    @ApiOperation(value = "返回审核枚举状态值", notes = "返回审核枚举状态值")
    @GetMapping("/anno/enums")
    public R<List<EnumsRo>> enums() {
        return success(Arrays.stream(CheckStatusEnums.values())
                .map(checkStatusEnums -> EnumsRo.builder()
                        .label(checkStatusEnums.getDesc())
                        .value(checkStatusEnums.getCode())
                        .build()
                )
                .collect(Collectors.toList())
        );
    }

}
