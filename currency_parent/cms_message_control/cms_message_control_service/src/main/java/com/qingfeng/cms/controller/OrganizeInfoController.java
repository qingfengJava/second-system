package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.organize.service.OrganizeInfoService;
import com.qingfeng.cms.domain.organize.dto.OrganizeInfoSaveDTO;
import com.qingfeng.cms.domain.organize.entity.OrganizeInfoEntity;
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
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.Map;



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

    /**
     * 列表
     */
    @GetMapping("/list")
    public R list(@RequestParam Map<String, Object> params){

        return success();
    }


    /**
     * 信息
     */
    @GetMapping("/info/{id}")
    public R info(@PathVariable("id") Long id){
		OrganizeInfoEntity organizeInfo = organizeInfoService.getById(id);

        return success();
    }

    @ApiOperation(value = "保存社团组织详情信息", notes = "保存社团组织详情信息")
    @PostMapping("/save")
    @SysLog("保存社团组织详情信息")
    public R save(@RequestBody @Validated OrganizeInfoSaveDTO organizeInfoSaveDTO){
		organizeInfoService.saveOrganizeInfo(organizeInfoSaveDTO);
        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody OrganizeInfoEntity organizeInfo){
		organizeInfoService.updateById(organizeInfo);

        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		organizeInfoService.removeByIds(Arrays.asList(ids));

        return success();
    }

}
