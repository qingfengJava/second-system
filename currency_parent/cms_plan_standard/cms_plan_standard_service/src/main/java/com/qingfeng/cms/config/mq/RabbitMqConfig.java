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
     * 声明项目模块邮箱和短信队列
     */
    public static final String QUEUE_INFORM_EMAIL = "email_queue";
    public static final String QUEUE_INFORM_SMS = "sms_queue";

    /**
     * 主题交换机名字
     */
    public static final String CRRM_EXCHANGE_DECLARE_INFORM="crrm_topic_exchange";

    /**
     * 路由键
     */
    public static final String ROUTINGKEY_EMAIL="*.inform.email";
    public static final String ROUTINGKEY_SMS="*.inform.sms";

    /**
     * 死信交换机
     */
    public static final String DEAD_LETTER_EXCHANGE = "crrm_dead_letter_exchange";
    /**
     * 死信队列的名称
     */
    public static final String DEAD_LETTER_QUEUE = "crrm_dead_queue";
    /**
     * 死信交换机routingkey
     */
    public static final String DEAD_LETTER_ROUTING_KEY="dead.key";

    /**
     * 声明  主题交换机
     * @return
     */
    @Bean(CRRM_EXCHANGE_DECLARE_INFORM)
    public TopicExchange EXCHANGE_TOPICS_INFORM(){
        return new TopicExchange(CRRM_EXCHANGE_DECLARE_INFORM);
    }

    /**
     * 声明 主题死信交换机
     * @return
     */
    @Bean(DEAD_LETTER_EXCHANGE)
    public TopicExchange DEAD_LETTER_EXCHANGE(){
        return new TopicExchange(DEAD_LETTER_EXCHANGE);
    }

    /**
     * 声明邮箱 QUEUE_INFORM_EMAIL队列
     * @return
     */
    @Bean(QUEUE_INFORM_EMAIL)
    public Queue QUEUE_INFORM_EMAIL(){
        //声明并绑定
        return QueueBuilder.durable(QUEUE_INFORM_EMAIL).withArguments(argsMents()).build();
    }

    /**
     * 声明短信 QUEUE_INFORM_SMS队列
     * @return
     */
    @Bean(QUEUE_INFORM_SMS)
    public Queue QUEUE_INFORM_SMS(){
        //声明并绑定
        return QueueBuilder.durable(QUEUE_INFORM_SMS).withArguments(argsMents()).build();
    }

    private Map<String, Object> argsMents() {
        Map<String, Object> args = new HashMap<>(3);
        //声明当前队列绑定的死信交换机
        args.put("x-dead-letter-exchange", DEAD_LETTER_EXCHANGE);
        //声明当前队列的死信路由 key
        args.put("x-dead-letter-routing-key", DEAD_LETTER_ROUTING_KEY);
        //声明队列的 TTL  10s超时未消费就说明消息消费失败，进入死信队列
        args.put("x-message-ttl", 10000);
        return args;
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
