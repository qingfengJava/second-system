package com.qingfeng.currency.authority.config.mq;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/12/18
 */
@Configuration
public class RabbitMqConfig {


    /**
     * 主题交换机名字
     */
    public static final String AUTH_EXCHANGE_DECLARE_INFORM="auth_direct_exchange";

    /**
     * 声明用户消息队列
     */
    public static final String QUEUE_USER_INFO = "user_info_queue";

    /**
     * 路由键
     */
    public static final String ROUTINGKEY_USER_INFO="user.info";

    /**
     * 死信交换机
     */
    public static final String DEAD_LETTER_EXCHANGE = "auth_dead_letter_exchange";
    /**
     * 死信队列的名称
     */
    public static final String DEAD_LETTER_QUEUE = "auth_dead_queue";
    /**
     * 死信交换机routingkey
     */
    public static final String DEAD_LETTER_ROUTING_KEY="dead.key";

    /**
     * 声明  路由交换机
     * @return
     */
    @Bean(AUTH_EXCHANGE_DECLARE_INFORM)
    public DirectExchange AUTH_EXCHANGE_DECLARE_INFORM(){
        return new DirectExchange(AUTH_EXCHANGE_DECLARE_INFORM);
    }

    /**
     * 声明 路由死信交换机
     * @return
     */
    @Bean(DEAD_LETTER_EXCHANGE)
    public DirectExchange DEAD_LETTER_EXCHANGE(){
        return new DirectExchange(DEAD_LETTER_EXCHANGE);
    }

    /**
     * 声明队列  并绑定死信
     * @return
     */
    @Bean(QUEUE_USER_INFO)
    public Queue QUEUE_USER_INFO(){
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        //声明队列的 TTL  15s超时未消费就说明消息消费失败，进入死信队列
        args.put("x-message-ttl", 15000);
        //声明并绑定
        return QueueBuilder.durable(QUEUE_USER_INFO).withArguments(args).build();
    }

    /**
     * 声明死信队列 DEAD_LETTER_QUEUE
     * @return
     */
    @Bean(DEAD_LETTER_QUEUE)
    public Queue DEAD_LETTER_QUEUE(){
        return new Queue(DEAD_LETTER_QUEUE);
    }

    /**
     * QUEUE_USER_INFO队列绑定交换机，指定routingKey
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding BINDING_ROUTINGKEY_AUTH_INFO(@Qualifier(QUEUE_USER_INFO) Queue queue,
                                            @Qualifier(AUTH_EXCHANGE_DECLARE_INFORM) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(ROUTINGKEY_USER_INFO).noargs();
    }

    /**
     * 声明死信队列 和交换机的绑定关系
     * @param queue
     * @param exchange
     * @return
     */
    @Bean
    public Binding deadLetterBindingQAD(@Qualifier(DEAD_LETTER_QUEUE) Queue queue,
                                        @Qualifier(DEAD_LETTER_EXCHANGE) Exchange exchange){
        return BindingBuilder.bind(queue).to(exchange).with(DEAD_LETTER_ROUTING_KEY).noargs();
    }

}
