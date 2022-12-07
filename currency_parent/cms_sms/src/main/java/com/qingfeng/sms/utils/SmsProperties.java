package com.qingfeng.sms.utils;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/7
 */
@Data
@Component
@ConfigurationProperties(prefix="tengyun.sms")
public class SmsProperties {

    private String appId;
    private String appKey;
    private String templateId;
    private String smsSinge;
}
