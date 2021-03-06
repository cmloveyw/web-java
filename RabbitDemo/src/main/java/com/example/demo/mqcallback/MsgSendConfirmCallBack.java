package com.example.demo.mqcallback;

import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @version 1.0
 * @类名: MsgSendConfirmCallBack.java
 * @描述: 消息发送到交换机确认机制
 *  //确认消息是否到达broker服务器，也就是只确认是否正确到达exchange中即可，只要正确的到达exchange中，
 *  broker即可确认该消息返回给客户端ack。
 * @创建人: CM
 * @创建时间: 2019-1-18
 */
public class MsgSendConfirmCallBack implements RabbitTemplate.ConfirmCallback {

    @Override
    public void confirm(CorrelationData correlationData, boolean b, String s) {
        System.out.println("MsgSendConfirmCallBack,回调" + correlationData);
        if (b) {
            System.out.println("消息发送到exchange成功");
        } else {
            System.out.println("消息消费失败：" + s + "\n重新发送");
        }
    }
}
