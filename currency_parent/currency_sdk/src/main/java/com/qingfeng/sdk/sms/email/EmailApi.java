package com.qingfeng.sdk.sms.email;

import com.qingfeng.sdk.sms.email.domain.EmailEntity;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/12
 */
@FeignClient(value = "cms-sms", fallback = EmailApiFallback.class)
@Component
public interface EmailApi {

    /**
     * 发送邮件信息
     * @param emailEntity
     * @return
     */
    @PostMapping("/email/send")
    public Integer sendEmail(@RequestBody EmailEntity emailEntity);
}
