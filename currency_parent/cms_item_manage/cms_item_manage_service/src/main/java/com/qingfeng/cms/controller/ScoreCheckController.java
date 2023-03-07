package com.qingfeng.cms.controller;

import com.qingfeng.cms.biz.check.service.ScoreCheckService;
import com.qingfeng.cms.domain.check.entity.ScoreCheckEntity;
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
 * 加分审核表
 *
 * @author 清风学Java
 * @version 3.0.0
 * @date 2023-03-07 11:12:55
 */
@Slf4j
@Validated
@RestController
@Api(value = "提供的相关功能", tags = "")
@RequestMapping("/score_check")
public class ScoreCheckController extends BaseController  {

    @Autowired
    private ScoreCheckService scoreCheckService;

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
		ScoreCheckEntity scoreCheck = scoreCheckService.getById(id);

        return success();
    }

    /**
     * 保存
     */
    @PostMapping("/save")
    public R save(@RequestBody ScoreCheckEntity scoreCheck){
		scoreCheckService.save(scoreCheck);

        return success();
    }

    /**
     * 修改
     */
    @PutMapping("/update")
    public R update(@RequestBody ScoreCheckEntity scoreCheck){
		scoreCheckService.updateById(scoreCheck);

        return success();
    }

    /**
     * 删除
     */
    @DeleteMapping("/delete")
    public R delete(@RequestBody Long[] ids){
		scoreCheckService.removeByIds(Arrays.asList(ids));

        return success();
    }

}
