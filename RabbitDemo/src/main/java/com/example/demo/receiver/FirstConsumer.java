package com.example.demo.receiver;

import com.example.demo.config.RabbitMqConfig;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @version 1.0
 * @类名: FirstConsumer.java
 * @描述:
 * @创建人: CM
 * @创建时间: 2019-1-18
 */
@Component
public class FirstConsumer {
    @RabbitListener(queues = {"first-queue","second-queue"}, containerFactory = "rabbitListenerContainerFactory")
    public void handleMessage(String message) {
        //处理消息
        System.out.println("FirstConsumer{} handleMessage:" + message);
    }
}
