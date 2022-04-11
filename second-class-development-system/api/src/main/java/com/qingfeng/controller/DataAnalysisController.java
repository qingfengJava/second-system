package com.qingfeng.controller;

import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 数据分析持久层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/4/12
 */
@RestController
@Api(value = "提供数据分析管理的相关接口", tags = "数据分析管理")
@RequestMapping("/analysis")
@CrossOrigin
public class DataAnalysisController {


}
