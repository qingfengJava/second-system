package com.qingfeng.cms.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/8
 */
public interface FileVodService {

    /**
     * 上传视频
     * @param file
     * @return
     */
    String uploadVod(MultipartFile file);

    /**
     * 删除视频
     * @param videoId
     */
    void removeVideo(String videoId);
}
