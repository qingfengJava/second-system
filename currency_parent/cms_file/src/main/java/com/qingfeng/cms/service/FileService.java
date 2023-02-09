package com.qingfeng.cms.service;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/8
 */
public interface FileService {

    /**
     * 上传头像文件的方法
     * @param file
     * @return
     */
    String upload(MultipartFile file) throws IOException;

    /**
     * 上传文件
     * @param file
     * @return
     * @throws IOException
     */
    String fileUpload(MultipartFile file) throws IOException;

    /**
     * 删除已上传的文件
     * @param fileUrl
     */
    void removeFile(String fileUrl);

    /**
     * 图片文件上传
     * @param file
     * @return
     */
    String uploadImg(MultipartFile file) throws IOException;
}
