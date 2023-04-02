package com.qingfeng.sms.service.impl;

import com.qingfeng.sms.entity.ShortMessageEntity;
import com.qingfeng.sms.service.ShortMessageService;
import org.springframework.stereotype.Service;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/7
 */
@Service
public class ShortMessageServiceImpl implements ShortMessageService {

    /**
     * 使用腾讯云发送短信
     * @param shortMessageEntity
     */
    @Override
    public void sendMessage(ShortMessageEntity shortMessageEntity) {
        // TODO 短信待封装
    }
}
