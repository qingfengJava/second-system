package com.qingfeng.sms.service;

import com.qingfeng.sms.entity.EmailEntity;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/7
 */
public interface EmailService {

    /**
     * 发送邮件信息
     * @param emailEntity
     * @return
     */
    Integer sendEmail(EmailEntity emailEntity);
}
