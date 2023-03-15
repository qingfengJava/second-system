package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.club.service.ClubScoreModuleService;
import com.qingfeng.cms.domain.club.dto.ClubScoreModuleSaveDTO;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 社团活动得分情况
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-15 11:50:15
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供社团活动得分情况的相关功能", tags = "提供社团活动得分情况的相关功能")
@RequestMapping("/club_score_module")
public class ClubScoreModuleController extends BaseController  {

    @Autowired
    private ClubScoreModuleService clubScoreModuleService;

    @ApiOperation(value = "保存社团得分情况", notes = "保存社团得分情况")
    @PostMapping("/save")
    @SysLog("保存社团得分情况")
    public R save(@RequestBody @Validated ClubScoreModuleSaveDTO clubScoreModuleSaveDTO){
		clubScoreModuleService.saveClubScoreModule(clubScoreModuleSaveDTO);
        return success();
    }

}
