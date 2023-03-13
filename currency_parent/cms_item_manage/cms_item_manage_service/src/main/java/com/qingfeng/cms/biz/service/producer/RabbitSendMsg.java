package com.qingfeng.cms.biz.service.producer;

import com.qingfeng.cms.config.mq.RabbitMqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageDeliveryMode;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/19
 */
@Component
public class RabbitSendMsg implements RabbitTemplate.ConfirmCallback, RabbitTemplate.ReturnCallback{

    private RabbitTemplate rabbitTemplate;

    @Autowired
    public RabbitSendMsg(RabbitTemplate rabbitTemplate) {
        super();
        this.rabbitTemplate = rabbitTemplate;
        this.rabbitTemplate.setMandatory(true);
        this.rabbitTemplate.setReturnCallback(this);
        this.rabbitTemplate.setConfirmCallback(this);
    }

    /**
     * 发布消息
     * @param
     */
    public void sendEmail(String message,String routingKey) {
        //在fanoutExchange中在绑定Q到X上时，会自动把Q的名字当作bindingKey。
        rabbitTemplate.convertAndSend(RabbitMqConfig.CRRM_EXCHANGE_DECLARE_INFORM,routingKey,this.setMessage(message));
    }


    /**
     * 设置消息参数
     * @param json
     * @return
     */
    private Message setMessage(String json){
        MessageProperties messageProperties = new MessageProperties();
        Message message = new Message(json.getBytes(), messageProperties);
        //消息持久化
        message.getMessageProperties().setDeliveryMode(MessageDeliveryMode.PERSISTENT);
        return message;
    }

    /**
     * 消息确认
     * @param correlationData
     * @param ack
     * @param cause
     */
    @Override
    public void confirm(CorrelationData correlationData, boolean ack, String cause) {
        if (ack) {
            System.out.println("消息发送确认成功==========");
        } else {
            System.out.println("消息发送失败=========="+ cause);
        }
    }

    /**
     * 消息发送失败回传
     * @param message
     * @param replyCode
     * @param replyText
     * @param exchange
     * @param routingKey
     */
    @Override
    public void returnedMessage(Message message, int replyCode, String replyText, String exchange, String routingKey) {
        System.out.println("return--message:" + new String(message.getBody()) + ",replyCode:" + replyCode + ",replyText:"
                + replyText + ",exchange:" + exchange + ",routingKey:" + routingKey);
        try {
            Thread.sleep(10000L);
            // TODO 重新发送消息至队列,此处应写一套重发机制,重发多少次结束,否则如果消息如果一直发送失败,则会一直发下去!
            this.rabbitTemplate.convertAndSend(exchange, routingKey, message);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}

