package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.dict.service.DictService;
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

        return success();
    }

    @ApiOperation(value = "修改数据字典内容", notes = "修改数据字典内容")
    @PutMapping("/update")
    @SysLog("修改数据字典内容")
    public R update(@ApiParam(value = "数据字典实体")
                        @RequestBody @Validated(SuperEntity.Update.class) DictUpdateDTO dictUpdateDTO) {
        dictService.updateDictById(dictUpdateDTO);
        return success();
    }

    @ApiOperation(value = "删除数据字典", notes = "根据Ids删除数据字典")
    @DeleteMapping("/delete")
    @SysLog("根据Id删除数据字典内容")
    public R delete(@ApiParam(value = "学分认定模块Id", required = true)
                    @RequestParam("ids[]") List<Long> ids) {
        dictService.removeByIds(ids);
        return success();
    }

}
