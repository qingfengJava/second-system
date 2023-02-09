package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.organize.service.OrganizeInfoService;
import com.qingfeng.cms.domain.organize.dto.OrganizeInfoSaveDTO;
import com.qingfeng.cms.domain.organize.dto.OrganizeInfoUpdateDTO;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
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
import org.springframework.web.bind.annotation.RestController;


/**
 * 社团组织详情表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:25
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供社团组织的相关功能", tags = "社团组织")
@RequestMapping("/organizeinfo")
public class OrganizeInfoController extends BaseController {

    @Autowired
    private OrganizeInfoService organizeInfoService;


    @ApiOperation(value = "根据用户Id查询社团组织详情信息", notes = "根据用户Id查询社团组织详情信息")
    @GetMapping("/info")
    @SysLog("根据用户Id查询社团组织详情信息")
    public R<OrganizeInfoEntity> info() {
        return success(organizeInfoService.getOne(Wraps.lbQ(new OrganizeInfoEntity())
                .eq(OrganizeInfoEntity::getUserId, this.getUserId())));
    }

    @ApiOperation(value = "保存社团组织详情信息", notes = "保存社团组织详情信息")
    @PostMapping("/save")
    @SysLog("保存社团组织详情信息")
    public R save(@RequestBody @Validated OrganizeInfoSaveDTO organizeInfoSaveDTO) {
        organizeInfoService.saveOrganizeInfo(organizeInfoSaveDTO, this.getUserId());
        return success();
    }

    @ApiOperation(value = "修改社团组织详情信息", notes = "修改社团组织详情信息")
    @PutMapping("/update")
    @SysLog("修改社团组织详情信息")
    public R update(@ApiParam(value = "社团信息修改实体")
                    @RequestBody @Validated(SuperEntity.Update.class) OrganizeInfoUpdateDTO organizeInfoUpdateDTO) {
        organizeInfoService.updateOrganizeInfoById(organizeInfoUpdateDTO, getUserId());
        return success();
    }

    @ApiOperation(value = "删除视频信息", notes = "删除视频信息")
    @DeleteMapping("/vod/{vodId}")
    @SysLog("删除视频信息")
    public R removeVod(@PathVariable("vodId") String vodId){
        organizeInfoService.removeVodId(vodId);
        return success();
    }

}
