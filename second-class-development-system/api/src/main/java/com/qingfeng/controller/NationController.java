package com.qingfeng.controller;

import com.qingfeng.constant.ResStatus;
import com.qingfeng.entity.Nation;
import com.qingfeng.service.NationService;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 户籍控制器
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/8
 */
@RestController
@RequestMapping("/nation")
@Api(value = "提供籍贯管理模块的功能",tags = "籍贯接口")
@CrossOrigin
public class NationController {

    @Autowired
    private NationService nationService;

    @ApiOperation("查询所有籍贯信息")
    @GetMapping("/findAll")
    public ResultVO getNation(){
        List<Nation> nations = nationService.findAll();
        return new ResultVO(ResStatus.OK,"success",nations);
    }
}
