package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.organize.service.OrganizeImgService;
import com.qingfeng.cms.domain.organize.dto.OrganizeImgSaveDTO;
import com.qingfeng.cms.domain.organize.entity.OrganizeImgEntity;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.dozer.DozerUtils;
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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;


/**
 * 社团组织图片信息
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供社团组织图片的相关功能", tags = "社团组织图片")
@RequestMapping("/organizeimg")
public class OrganizeImgController extends BaseController {

    @Autowired
    private OrganizeImgService organizeImgService;
    @Autowired
    private DozerUtils dozerUtils;

    @ApiOperation(value = "根据社团Id查询社团设置的图片", notes = "根据社团Id查询社团设置的图片")
    @GetMapping("/list/{organizeId}")
    @SysLog("根据社团Id查询社团设置的图片")
    public R<List<OrganizeImgEntity>> list(@PathVariable("organizeId") Long organizeId) {
        return success(organizeImgService.getImgList(organizeId, this.getUserId()));
    }

    @ApiOperation(value = "保存社团设置的图片信息", notes = "保存社团设置的图片的信息")
    @PostMapping("/save")
    @SysLog("保存社团设置的图片信息")
    public R save(@ApiParam(value = "社团图片信息实体", required = true)
                              @RequestBody @Validated OrganizeImgSaveDTO organizeImgSaveDTO) {
        organizeImgService.save(dozerUtils.map2(organizeImgSaveDTO, OrganizeImgEntity.class));
        return success();
    }

    @ApiOperation(value = "根据Id删除图片信息", notes = "根据Id删除图片信息")
    @DeleteMapping("/{id}")
    @SysLog("根据Id删除图片信息")
    public R delete(@PathVariable("id") Long id) {
        organizeImgService.removeById(id);

        return success();
    }

    @ApiOperation(value = "根据社团Ids查询社团设置的图片", notes = "根据社团Ids查询社团设置的图片")
    @PostMapping("/list/img")
    @SysLog("根据社团Id查询社团设置的图片")
    public R<Map<Long, List<OrganizeImgEntity>>> listImg(@RequestBody List<Long> organizeIds) {
        return success(organizeImgService.getImgLists(organizeIds));
    }
}
