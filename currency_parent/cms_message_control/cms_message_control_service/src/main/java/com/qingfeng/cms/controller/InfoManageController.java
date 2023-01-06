package com.qingfeng.cms.controller;

import cn.hutool.core.util.ObjectUtil;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.qingfeng.cms.biz.manage.service.InfoManageService;
import com.qingfeng.cms.domain.manage.dto.InfoManagePageDTO;
import com.qingfeng.cms.domain.manage.dto.InfoManageSaveDTO;
import com.qingfeng.cms.domain.manage.entity.InfoManageEntity;
import com.qingfeng.cms.domain.manage.enums.TypeStatusEnum;
import com.qingfeng.cms.domain.manage.vo.InfoTypeVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.database.mybatis.conditions.Wraps;
import com.qingfeng.currency.database.mybatis.conditions.query.LbqWrapper;
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

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;


/**
 * 信息管理
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-12-30 17:01:52
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供信息管理的相关功能", tags = "提供信息管理的相关功能")
@RequestMapping("/infomanage")
public class InfoManageController extends BaseController {

    @Autowired
    private InfoManageService infoManageService;

    @ApiImplicitParams({
            @ApiImplicitParam(name = "current", value = "当前页", dataType = "long", paramType = "query", defaultValue = "1"),
            @ApiImplicitParam(name = "size", value = "每页显示几条", dataType = "long", paramType = "query", defaultValue = "15"),
    })
    @ApiOperation(value = "信息管理列表", notes = "信息管理列表")
    @GetMapping("/list")
    @SysLog("查询信息管理列表")
    public R<IPage<InfoManageEntity>> list(InfoManagePageDTO infoManagePageDTO) {
        IPage<InfoManageEntity> page = getPage();

        LbqWrapper<InfoManageEntity> query = Wraps.lbQ(new InfoManageEntity())
                .orderByDesc(InfoManageEntity::getEndTime);
        if (ObjectUtil.isNotEmpty(infoManagePageDTO.getStartTime())) {
            query.geHeader(InfoManageEntity::getStartTime, infoManagePageDTO.getStartTime());
        }
        if (ObjectUtil.isNotEmpty(infoManagePageDTO.getEndTime())) {
            query.leFooter(InfoManageEntity::getEndTime, infoManagePageDTO.getEndTime());
        }
        if (ObjectUtil.isNotEmpty(infoManagePageDTO.getTypeStatus())) {
            query.eq(InfoManageEntity::getTypeStatus, infoManagePageDTO.getTypeStatus());
        }

        infoManageService.page(page, query);

        return success(page);
    }

    @ApiOperation(value = "任务状态枚举", notes = "任务状态枚举")
    @GetMapping("/InfoType")
    @SysLog("查询信息管理列表")
    public R getInfoTypeEnums() {
        List<InfoTypeVo> typeVoList = Arrays.stream(TypeStatusEnum.values()).map(typeStatusEnum ->
                InfoTypeVo.builder()
                        .label(typeStatusEnum.getDesc())
                        .value(typeStatusEnum.name())
                        .build()
        ).collect(Collectors.toList());
        return success(typeVoList);
    }

    @ApiOperation(value = "信息管理实体保存", notes = "信息管理实体保存")
    @PostMapping("/save")
    @SysLog("信息管理实体保存")
    public R save(@ApiParam(value = "信息管理保存实体", required = true)
                  @RequestBody @Validated InfoManageSaveDTO infoManageSaveDTO) {
        infoManageService.saveInfoManage(infoManageSaveDTO);
        return success();
    }

    @ApiOperation(value = "废弃信息管理任务", notes = "废弃信息管理任务")
    @PutMapping("/{id}")
    @SysLog("废弃信息管理任务")
    public R update(@ApiParam(value = "任务Id", required = true)
                    @PathVariable("id") Long id) {
        InfoManageEntity infoManageEntity = InfoManageEntity.builder()
                .typeStatus(TypeStatusEnum.OBSOLETE)
                .build();
        infoManageEntity.setId(id);
        infoManageService.updateById(infoManageEntity);
        return success();
    }


    @ApiOperation(value = "删除已执行", notes = "删除已执行")
    @DeleteMapping
    @SysLog("删除已执行")
    public R delete() {
        infoManageService.remove(Wraps.lbQ(new InfoManageEntity())
                .eq(InfoManageEntity::getTypeStatus, TypeStatusEnum.COMPLETED));
        return success();
    }

    @ApiOperation(value = "根据Id删除信息管理任务", notes = "根据Id删除信息管理任务")
    @DeleteMapping("/id")
    @SysLog("根据Id删除信息管理任务")
    public R deleteById(@ApiParam(value = "任务Id", required = true)
                        @RequestParam("id") Long id) {
        infoManageService.removeById(id);
        return success();
    }

}
