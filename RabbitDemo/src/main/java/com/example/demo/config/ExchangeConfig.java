package com.example.demo.config;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @类名: ExchangeConfig.java
 * @描述: 消息交换机配置，可以配置多个
 * @创建人: CM
 * @创建时间: 2019-1-18
 */
@Configuration
public class ExchangeConfig {

    /**
     * 1、定义direct exchange ,绑定queueTest
     * 2、durable = "true" rabbitmq重启的时候不需要创建新的交换机
     * 3、direct交换器相对来说比较简单，匹配规则为：如果路由键匹配，消息就被投送到相应的队列
     * 时间:   2019-1-18 14:22
     * 作者:   CM
     * @param
     * @return org.springframework.amqp.core.DirectExchange
     */
    @Bean
    public DirectExchange directExchange(){
        DirectExchange directExchange = new DirectExchange(RabbitMqConfig.EXCHANGE,true,false);
        return directExchange;
    }
}
