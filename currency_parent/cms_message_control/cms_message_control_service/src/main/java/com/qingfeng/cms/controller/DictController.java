package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.dict.service.DictService;
import com.qingfeng.cms.domain.dict.entity.DictEntity;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import io.swagger.annotations.Api;
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
@RequestMapping("message/dict")
public class DictController extends BaseController {

    @Autowired
    private DictService dictService;

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
		DictEntity dict = dictService.getById(id);

        return success();
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody DictEntity dict){
		dictService.save(dict);

        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody DictEntity dict){
		dictService.updateById(dict);

        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		dictService.removeByIds(Arrays.asList(ids));

        return success();
    }

}
