package com.example.demo.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @类名: QueueConfig.java
 * @描述: 队列配置，可以配置多个队列
 * @创建人: CM
 * @创建时间: 2019-1-18
 */
@Configuration
public class QueueConfig {
    /**
     * durable = "true" 数据持久化 rabbitmq重启的时候不需要创建新的队列
     * autoDelete 表示消息队列没有在使用的时候将被自动删除，默认是false
     * exclusive 表示该消息队列是否只在当前connection生效，默认是false
     * 时间:   2019-1-18 14:29
     * 作者:   CM
     * @param
     * @return org.springframework.amqp.core.Queue
     */
    @Bean
    public Queue firstQueue(){
        return new Queue("first-queue",true,false,false);
    }

    @Bean
    public Queue secondQueue(){
        return new Queue("second-queue",true,false,false);
    }
}
