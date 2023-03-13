package com.qingfeng.cms.config.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/18
 */
@Configuration
public class RabbitMqConfig {

    /**
     * 声明项目模块邮箱和短信队列
     */
    public static final String QUEUE_INFORM_EMAIL = "email_queue_item";
    public static final String QUEUE_INFORM_SMS = "sms_queue_item";

    /**
     * 主题交换机名字
     */
    public static final String CRRM_EXCHANGE_DECLARE_INFORM="crrm_topic_exchange_item";

    /**
     * 路由键
     */
    public static final String ROUTINGKEY_EMAIL="*.item.email";
    public static final String ROUTINGKEY_SMS="*.item.sms";

    /**
     * 声明  主题交换机
     * @return
     */
    @Bean(CRRM_EXCHANGE_DECLARE_INFORM)
    public TopicExchange EXCHANGE_TOPICS_INFORM(){
        return new TopicExchange(CRRM_EXCHANGE_DECLARE_INFORM);
    }


    /**
     * 声明邮箱 QUEUE_INFORM_EMAIL队列
     * @return
     */
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL(){
        //声明并绑定
        return QueueBuilder.durable(QUEUE_INFORM_EMAIL).build();
    }

    /**
     * 声明短信 QUEUE_INFORM_SMS队列
     * @return
     */
    @Bean(QUEUE_INFORM_SMS)
    public Queue QUEUE_INFORM_SMS(){
        //声明并绑定
        return QueueBuilder.durable(QUEUE_INFORM_SMS).build();
    }



    /**
     * 绑定队列 ROUTINGKEY_SMS队列绑定交换机，指定routingKey
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_ROUTINGKEY_SMS(@Qualifier(QUEUE_INFORM_SMS) Queue queue,
                                          @Qualifier(CRRM_EXCHANGE_DECLARE_INFORM) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_SMS).noargs();
    }

    /**
     * QUEUE_INFORM_EMAIL队列绑定交换机，指定routingKey
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_ROUTINGKEY_EMAIL(@Qualifier(QUEUE_INFORM_EMAIL) Queue queue,
                                            @Qualifier(CRRM_EXCHANGE_DECLARE_INFORM) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_EMAIL).noargs();
    }

}
