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
}
