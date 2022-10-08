package com.qingfeng.cms.controller;

import com.qingfeng.cms.service.FileService;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import com.qingfeng.currency.exception.BizException;
import com.qingfeng.currency.exception.code.ExceptionCode;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/8
 */
@Api(value = "提供文件上传的相关接口", tags = "文件上传服务")
@RestController
@RequestMapping("/file")
@Slf4j
public class FileOssController extends BaseController {

    @Autowired
    private FileService fileService;

    @ApiOperation(value = "头像文件上传", notes = "头像文件上传")
    @PostMapping("/upload")
    public R fileUpload(@ApiParam(value = "文件", required = true)
                            @RequestParam("file") MultipartFile file){
        try {
            String uploadUrl = fileService.upload(file);

            //返回r对象
            return success(uploadUrl);
        } catch (Exception e) {
            throw new BizException(ExceptionCode.OPERATION_EX.getCode(),ExceptionCode.OPERATION_EX.getMsg());
        }
    }
}