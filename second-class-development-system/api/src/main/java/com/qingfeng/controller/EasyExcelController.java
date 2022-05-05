package com.qingfeng.controller;

import com.qingfeng.service.EasyExcelService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;

/**
 * 文件导入导出控制层
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/5/5
 */
@RestController
@Api(value = "文件导入导出控制层", tags = "文件导入导出控制层")
@RequestMapping("/excel")
@CrossOrigin
public class EasyExcelController {

    @Autowired
    private EasyExcelService easyExcelService;

    @ApiOperation("用户文件导出（学生/校领导）")
    @GetMapping("/user/export")
    public void userExport(HttpServletResponse response) {
        easyExcelService.userExport(response);
    }

    @ApiOperation("用户文件导入（学生/校领导）")
    @PostMapping("/user/import")
    public void userImport(MultipartFile file) {
        easyExcelService.userImport(file);
    }
}
