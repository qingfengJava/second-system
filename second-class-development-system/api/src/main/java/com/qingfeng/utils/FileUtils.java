package com.qingfeng.utils;

import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 封装文件相关的操作方法
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/1/12
 */
public class FileUtils {

    /**
     * 文件上传头像方法抽取
     * @param img
     * @throws IOException
     */
    public static String uploadFile(MultipartFile img,String realPath) throws IOException {
        //获取新上传的文件的名称
        String originalFilename = img.getOriginalFilename();
        //通过时间戳创建文件的前缀
        String fileNamePrefix = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
        //获取文件的后缀
        String fileNameSuffix = originalFilename.substring(originalFilename.lastIndexOf("."));
        //新头像文件的名字
        String newFileName = fileNamePrefix+fileNameSuffix;
        //上传头像到文件
        img.transferTo(new File(realPath,newFileName));
        return newFileName;
    }
}
