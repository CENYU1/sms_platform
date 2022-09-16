package com.qf.config;

import com.qf.constant.RabbitMQConstants;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 创建队列和交换机
 * 给短信对象StandardSubmit使用
 */
@Configuration
public class RabbitConfig {


    // 交换机
    @Bean
    public Exchange preSendExchange(){
        return ExchangeBuilder.topicExchange(RabbitMQConstants.TOPIC_PRE_SEND + "_exchange").build();
    }

    // 队列
    @Bean
    public Queue preSendQueue(){
        return QueueBuilder.durable(RabbitMQConstants.TOPIC_PRE_SEND).build();
    }

    // 绑定
    @Bean
    public Binding preSendBinding(Queue preSendQueue,Exchange preSendExchange){
        return BindingBuilder.bind(preSendQueue).to(preSendExchange).with(RabbitMQConstants.BINDING).noargs();
    }


}
