package com.qingfeng.cms.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/10/8
 */
@Data
@Component
@ConfigurationProperties(prefix="file.oss")
public class FileOssProperties {

    private String accesskey;
    private String secretKey;
    private String endpoint;
    private String bucket;
}
