package com.qingfeng.cms.controller;

import com.qingfeng.cms.service.FileVodService;
import com.qingfeng.currency.base.BaseController;
import com.qingfeng.currency.base.R;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2023/2/8
 */
@Api(value = "提供视频文件上传的相关接口", tags = "视频文件上传服务")
@RestController
@RequestMapping("/file/vod")
@Slf4j
public class FileVodController extends BaseController {

    @Autowired
    private FileVodService fileVodService;

    @ApiOperation(value = "上传视频接口", notes = "上传视频接口")
    @PostMapping("/vod_upload")
    public R<String> uploadVideo(
            @ApiParam(name = "file", value = "视频文件", required = true)
            @RequestParam("file") MultipartFile file) throws Exception {
        String videoId = fileVodService.uploadVod(file);
        return success(videoId);
    }

    @ApiOperation(value = "删除视频接口", notes = "删除视频接口")
    @DeleteMapping("/{videoId}")
    public R removeVideo(@ApiParam(name = "videoId", value = "云端视频id", required = true)
                         @PathVariable String videoId){

        fileVodService.removeVideo(videoId);
        return success();
    }
}
