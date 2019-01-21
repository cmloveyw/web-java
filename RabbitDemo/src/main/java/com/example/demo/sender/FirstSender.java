package com.example.demo.sender;

import com.example.demo.config.RabbitMqConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @类名: FirstSender.java
 * @描述: 消息发送 生产者1
 * @创建人: CM
 * @创建时间: 2019-1-18
 */
@Component
public class FirstSender {
    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 发送消息
     * 时间:   2019-1-18 15:14
     * 作者:   CM
     * @param uuid
     * @param message
     * @return void
     */
    public void send(String uuid,Object message){
        /*Message message1 = MessageBuilder.withBody(message.getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setCorrelationId(uuid).build();*/

        CorrelationData correlationData = new CorrelationData(uuid);
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE,RabbitMqConfig.ROUTINGKEY1,message,correlationData);
    }
}
