package com.qingfeng.controller;

import com.qingfeng.entity.Check;
import com.qingfeng.service.ActiveCheckService;
import com.qingfeng.utils.FileUtils;
import com.qingfeng.vo.ResultVO;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/6/7
 */
@RestController
@RequestMapping("/check")
@Api(value = "提供活动审核相关的接口功能",tags = "活动审核管理")
@CrossOrigin
public class ActiveCheckController {

    @Autowired
    private ActiveCheckService activeCheckService;

    /**
     * 文件路径
     */
    @Value("${photo.file.dir}")
    private String realPath;

    @ApiOperation("根据活动Id查询活动审查的详情信息")
    @GetMapping("/selectActiveDetails/{activeId}")
    public ResultVO selectActiveDetails(@PathVariable("activeId") Integer activeId){
        return activeCheckService.selectActiveDetailsById(activeId);
    }

    @ApiOperation("上传活动图片")
    @PostMapping("/uploadActiveImg/{applyId}")
    public void uploadActiveImg(@PathVariable("applyId") Integer applyId, MultipartFile file) {
        try {
            boolean notEmpty = (file != null);
            String newFileName = null;
            //不为空
            if (notEmpty) {
                //处理新的头像上传   1、处理头像的上传 & 修改文件名
                newFileName = FileUtils.uploadFile(file, realPath);
            }
            //调用处理图片的方法
            activeCheckService.uploadActiveImg(applyId, newFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @ApiOperation("指定删除上传的图片")
    @DeleteMapping("/deleteActiveImg/{applyId}")
    public ResultVO deleteActiveImg(@PathVariable("applyId") Integer applyId, String fileName) {
        //检查就照片是否存在，存在就将其删除掉，再保存新照片
        File newFile = new File(realPath, fileName);
        if (newFile.exists()) {
            newFile.delete();
        }
        return activeCheckService.deleteActiveImg(applyId,fileName);
    }

    @ApiOperation("提交审核内容")
    @PostMapping("/submitCheck")
    public ResultVO submitCheck(@RequestBody Check check) {
        return activeCheckService.submitCheck(check);
    }

}
