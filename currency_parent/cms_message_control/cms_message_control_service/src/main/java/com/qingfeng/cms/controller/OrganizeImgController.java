package com.qingfeng.cms.controller;

import cn.hutool.core.util.ObjectUtil;
import com.qingfeng.cms.biz.organize.service.OrganizeImgService;
import com.qingfeng.cms.domain.organize.entity.OrganizeImgEntity;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


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

    @ApiOperation(value = "根据社团Id查询社团设置的图片", notes = "根据社团Id查询社团设置的图片")
    @GetMapping("/list/{organizeId}")
    @SysLog("根据社团Id查询社团设置的图片")
    public R<List<OrganizeImgEntity>> list(@PathVariable("organizeId") Long organizeId) {
        return success(organizeImgService.getImgList(organizeId, this.getUserId()));
    }

    @ApiOperation(value = "保存社团设置的图片信息", notes = "保存社团设置的图片的信息")
    @PostMapping("/save/{organizeId}")
    @SysLog("保存社团设置的图片信息")
    public R save(
            @PathVariable("organizeId") Long organizeId,
            @ApiParam(name = "file", value = "图片文件", required = true)
            @RequestParam("file") MultipartFile file) {
        if (ObjectUtil.isEmpty(organizeId)){
            return fail("社团信息未完善");
        }
        organizeImgService.saveOrganizeImg(organizeId, file);
        return success();
    }

    @ApiOperation(value = "根据Id删除图片信息", notes = "根据Id删除图片信息")
    @DeleteMapping("/{id}")
    public R delete(@PathVariable("id") Long id) {
        organizeImgService.removeById(id);

        return success();
    }

}
