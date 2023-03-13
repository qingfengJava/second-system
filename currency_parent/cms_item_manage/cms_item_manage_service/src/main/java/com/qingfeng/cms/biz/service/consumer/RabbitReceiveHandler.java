package com.qingfeng.cms.biz.service.consumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.qingfeng.cms.config.mq.RabbitMqConfig;
import com.qingfeng.sdk.sms.email.EmailApi;
import com.qingfeng.sdk.sms.email.domain.EmailEntity;
import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/19
 */
@Component
public class RabbitReceiveHandler {

    @Autowired
    private EmailApi emailApi;
    @Autowired
    private ObjectMapper objectMapper;


    /**
     * 监听邮箱队列的消费
     * @param message
     * @param channel
     * @throws IOException
     */
    @RabbitListener(queues = {RabbitMqConfig.QUEUE_INFORM_EMAIL})
    public void send_email(Message message, Channel channel) throws IOException {

        //进行邮件的发送
        emailApi.sendEmail(objectMapper.readValue(
                new String(message.getBody(), StandardCharsets.UTF_8),
                EmailEntity.class));

        // 手动应答
        channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
    }

    // TODO 监听短信队列的消费

}
