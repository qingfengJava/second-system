package com.qingfeng.sms.service;

import com.qingfeng.sms.entity.ShortMessageEntity;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/7
 */
public interface ShortMessageService {

    /**
     * 使用腾讯云发送短信
     * @param shortMessageEntity
     */
    void sendMessage(ShortMessageEntity shortMessageEntity);
}
