package com.qf.config;

import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 创建延时队列和交换器
 * 将队列和交换器绑定, 
 * 给StandardReport短信状态报告推送使用
 */
@Configuration
public class DelayedConfig {

    //交换机名字
    public static final String DELAYED_EXCHANGE = "delayed_exchange";
    //队列名字
    public static final String DELAYED_QUEUE = "delayed_queue";
    public static final String DELAYED_ROUTING_KEY = "#";


    @Bean
    public Exchange delayedExchange(){
        Map<String, Object> args = new HashMap<>();
        // direct,fanout,topic,headers
        // 指定当前延迟交换机的具体路由类型
        args.put("x-delayed-type","topic");
        CustomExchange exchange = new CustomExchange(DELAYED_EXCHANGE,"x-delayed-message",true,false,args);
        return exchange;
    }

    @Bean
    public Queue delayedQueue(){
        return QueueBuilder.durable(DELAYED_QUEUE).build();
    }

    @Bean
    public Binding delayedBinding(Queue delayedQueue,Exchange delayedExchange){
        return BindingBuilder.bind(delayedQueue).to(delayedExchange).with(DELAYED_ROUTING_KEY).noargs();
    }

}