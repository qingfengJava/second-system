package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.dict.service.DictEasyExcelService;
import com.qingfeng.cms.biz.dict.service.DictService;
import com.qingfeng.cms.constant.CacheKey;
import com.qingfeng.cms.domain.dict.dto.DictSaveDTO;
import com.qingfeng.cms.domain.dict.dto.DictUpdateDTO;
import com.qingfeng.cms.domain.dict.entity.DictEntity;
import com.qingfeng.cms.domain.dict.vo.DictVo;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.base.entity.SuperEntity;
import com.qingfeng.currency.log.annotation.SysLog;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import net.oschina.j2cache.CacheChannel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * 组织架构表   数据字典
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:27
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供数据字典的相关功能", tags = "组织架构表   数据字典")
@RequestMapping("/dict")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;
    @Autowired
    private DictEasyExcelService dictEasyExcelService;
    @Autowired
    private CacheChannel cacheChannel;

    @ApiOperation(value = "查询数据字典树", notes = "查询数据字典树")
    @GetMapping("/list")
    @SysLog("查询数据字典数")
    public R<List<DictVo>> list() {
        List<DictVo> dictVoList = dictService.findListTree();
        return success(dictVoList);
    }

    @ApiOperation(value = "根据Id查询数据字典信息", notes = "根据Id查询数据字典信息")
    @GetMapping("/info/{dictId}")
    @SysLog("根据Id查询数据字典信息")
    public R<DictEntity> info(@ApiParam(value = "学分认定模块Id", required = true)
                              @PathVariable("dictId") @NotNull Long dictId) {
        DictEntity dictEntity = dictService.getById(dictId);
        return success(dictEntity);
    }

    @ApiOperation(value = "添加数据字典内容", notes = "添加数据字典内容")
    @PostMapping("/save")
    @SysLog("添加数据字典内容")
    public R save(@ApiParam(value = "数据字典保存实体", required = true)
                  @RequestBody @Validated DictSaveDTO dictSaveDTO) {
        dictService.saveDict(dictSaveDTO);
        //清除缓存
        cacheChannel.evict(CacheKey.MESSAGE_RESOURCE, CacheKey.DICT_TREE);
        cacheChannel.evict(CacheKey.MESSAGE_RESOURCE, CacheKey.DICT_EXCEL_LIST);
        return success();
    }

    @ApiOperation(value = "修改数据字典内容", notes = "修改数据字典内容")
    @PutMapping("/update")
    @SysLog("修改数据字典内容")
    public R update(@ApiParam(value = "数据字典实体")
                    @RequestBody @Validated(SuperEntity.Update.class) DictUpdateDTO dictUpdateDTO) {
        dictService.updateDictById(dictUpdateDTO);
        //清除缓存
        cacheChannel.evict(CacheKey.MESSAGE_RESOURCE, CacheKey.DICT_TREE);
        cacheChannel.evict(CacheKey.MESSAGE_RESOURCE, CacheKey.DICT_EXCEL_LIST);
        return success();
    }

    @ApiOperation(value = "删除数据字典", notes = "根据Ids删除数据字典")
    @PostMapping("/delete")
    @SysLog("根据Id删除数据字典内容")
    public R delete(@ApiParam(value = "学分认定模块Id", required = true)
                    @RequestBody List<Long> ids) {
        dictService.removeByIds(ids);
        //清除缓存
        cacheChannel.evict(CacheKey.MESSAGE_RESOURCE, CacheKey.DICT_TREE);
        cacheChannel.evict(CacheKey.MESSAGE_RESOURCE, CacheKey.DICT_EXCEL_LIST);
        return success();
    }

    @ApiOperation(value = "导出数据字典模板", notes = "导出数据字典模板")
    @GetMapping("/excel/export_template")
    @SysLog("导出数据字典Excel模板")
    public void exportTemplate(HttpServletResponse response) {
        dictEasyExcelService.exportTemplate(response);
    }

    @ApiOperation(value = "导出数据字典", notes = "导出数据字典")
    @GetMapping("/excel/export_dict")
    @SysLog("导出数据字典Excel")
    public void exportDict(HttpServletResponse response) {
        dictEasyExcelService.exportDict(response);
    }

    @ApiOperation(value = "数据字典Excel导入", notes = "数据字典Excel导入")
    @PostMapping("/excel/import")
    @SysLog("数据字典Excel导入")
    public R userImport(MultipartFile file) {
        dictEasyExcelService.importDict(file);
        //清除缓存
        cacheChannel.evict(CacheKey.MESSAGE_RESOURCE, CacheKey.DICT_TREE);
        cacheChannel.evict(CacheKey.MESSAGE_RESOURCE, CacheKey.DICT_EXCEL_LIST);
        return success();
    }

    @ApiOperation(value = "查询所有的学院", notes = "查询所有的学院")
    @GetMapping("/find/department")
    @SysLog("查询所有的学院")
    public R<List<DictEntity>> findDepartment() {
        List<DictEntity> dictEntityList = dictService.findDepartment();
        return success(dictEntityList);
    }

    @ApiOperation(value = "根据学院Id，查询对应的专业", notes = "根据学院Id，查询对应的专业")
    @GetMapping("/find/major/{depId}")
    @SysLog("根据学院Id，查询对应的专业")
    public R<List<DictEntity>> findMajor(@ApiParam(value = "院系Id")
                                         @PathVariable("depId") Long depId) {
        List<DictEntity> dictEntityList = dictService.findMajorByDepId(depId);
        return success(dictEntityList);
    }

    @ApiOperation(value = "查询民族", notes = "查询民族")
    @GetMapping("/find/nation")
    @SysLog("查询民族")
    public R<List<DictEntity>> findNation() {
        List<DictEntity> dictEntityList = dictService.findNation();
        return success(dictEntityList);
    }

    @ApiOperation(value = "查询省市", notes = "查询省市")
    @GetMapping("/find/native")
    @SysLog("查询省市")
    public R<List<DictVo>> findNativePlace() {
        List<DictVo> dictVoList = dictService.findNativePlace();
        return success(dictVoList);
    }
}
