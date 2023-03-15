package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.total.service.StudentScoreTotalService;
import com.qingfeng.cms.domain.total.entity.StudentScoreTotalEntity;
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
 * 用户总得分情况
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-15 11:50:15
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供用户总得分情况的相关功能", tags = "提供用户总得分情况的相关功能")
@RequestMapping("/student_score_total")
public class StudentScoreTotalController extends BaseController  {

    @Autowired
    private StudentScoreTotalService studentScoreTotalService;

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
		StudentScoreTotalEntity studentScoreTotal = studentScoreTotalService.getById(id);

        return success();
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody StudentScoreTotalEntity studentScoreTotal){
		studentScoreTotalService.save(studentScoreTotal);

        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody StudentScoreTotalEntity studentScoreTotal){
		studentScoreTotalService.updateById(studentScoreTotal);

        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		studentScoreTotalService.removeByIds(Arrays.asList(ids));

        return success();
    }

}
