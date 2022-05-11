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

    @ApiOperation("社团信息列表导出")
    @GetMapping("/club/export")
    public void clubExport(HttpServletResponse response) {
        easyExcelService.clubExport(response);
    }

    @ApiOperation("社团信息列表导入")
    @PostMapping("/club/import")
    public void clubImport(MultipartFile file) {
        easyExcelService.clubImport(file);
    }

    @ApiOperation("学生学籍信息批量导出")
    @GetMapping("/stuInfo/export")
    public void stuInfoExport(HttpServletResponse response) {
        easyExcelService.stuInfoExport(response);
    }

    @ApiOperation("学生学籍信息批量导入")
    @PostMapping("/stuInfo/import")
    public void stuInfoImport(MultipartFile file) {
        easyExcelService.stuInfoImport(file);
    }

    @ApiOperation("校领导信息批量导出")
    @GetMapping("/leader/export")
    public void leaderExport(HttpServletResponse response) {
        easyExcelService.leaderExport(response);
    }

    @ApiOperation("校领导信息批量导入")
    @PostMapping("/leader/import")
    public void leaderImport(MultipartFile file) {
        easyExcelService.leaderImport(file);
    }
}
