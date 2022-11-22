package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.college.service.CollegeInformationService;
import com.qingfeng.cms.domain.college.entity.CollegeInformationEntity;
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
 * 院级信息（包含班级），数据字典
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2022-11-22 22:53:26
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供院级信息的相关功能", tags = "院级信息（包含班级），数据字典")
@RequestMapping("message/collegeinformation")
public class CollegeInformationController extends BaseController {

    @Autowired
    private CollegeInformationService collegeInformationService;

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
		CollegeInformationEntity collegeInformation = collegeInformationService.getById(id);

        return success();
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody CollegeInformationEntity collegeInformation){
		collegeInformationService.save(collegeInformation);

        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody CollegeInformationEntity collegeInformation){
		collegeInformationService.updateById(collegeInformation);

        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		collegeInformationService.removeByIds(Arrays.asList(ids));

        return success();
    }

}
