package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.sign.service.ActiveSignService;
import com.qingfeng.cms.domain.sign.dto.ActiveApplySignQueryDTO;
import com.qingfeng.cms.domain.sign.dto.ActiveQueryDTO;
import com.qingfeng.cms.domain.sign.dto.ActiveSignSaveDTO;
import com.qingfeng.cms.domain.sign.vo.ActiveApplySignVo;
import com.qingfeng.cms.domain.sign.vo.ApplyPageVo;
import com.qingfeng.cms.domain.sign.vo.OrganizeVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;



/**
 * 活动报名表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-01-31 11:41:11
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供活动报名的相关功能", tags = "提供活动报名的相关功能")
@RequestMapping("/active_sign")
public class ActiveSignController extends BaseController  {

    @Autowired
    private ActiveSignService activeSignService;

    @ApiOperation(value = "查询所有的社团组织信息列表", notes = "查询所有的社团组织信息列表")
    @GetMapping("/anno/organize/list")
    @SysLog("查询所有的社团组织信息列表")
    public R<List<OrganizeVo>> organizeList(){
        return success(activeSignService.organizeList());
    }

    @ApiOperation(value = "查询所有已发布的活动", notes = "查询所有已发布的活动")
    @PostMapping("/apply/list")
    @SysLog("查询所有已发布的活动")
    public R<ApplyPageVo> applyList(@RequestBody @Validated ActiveQueryDTO activeQueryDTO){
        return success(activeSignService.applyList(activeQueryDTO, getUserId()));
    }

    @ApiOperation(value = "查询用户已报名的活动信息", notes = "查询用户已报名的活动信息")
    @PostMapping("/apply/sign")
    @SysLog("查询用户已报名的活动信息")
    public R<ActiveApplySignVo> getActiveSignList(@RequestBody @Validated ActiveApplySignQueryDTO activeApplySignQueryDTO){
        // 设计查询用户已经报名的活动信息
        return success(activeSignService.getActiveSignList(activeApplySignQueryDTO, getUserId()));
    }

    @ApiOperation(value = "活动报名", notes = "活动报名")
    @PostMapping("/sign")
    @SysLog("活动报名")
    public R save(@RequestBody @Validated ActiveSignSaveDTO activeSignSaveDTO){
		activeSignService.saveSign(activeSignSaveDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "取消活动报名", notes = "取消活动报名")
    @DeleteMapping("/delete/{id}")
    @SysLog("取消活动报名")
    public R delete(@PathVariable("id") Long id){
		//取消活动报名，直接根据id删除报名信息
        activeSignService.removeById(id);
        return success();
    }

}
