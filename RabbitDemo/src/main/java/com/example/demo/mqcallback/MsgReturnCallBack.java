package com.example.demo.mqcallback;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

/**
 * @version 1.0
 * @类名: MsgReturnCallBack.java
 * @描述: 当消息从交换机到队列失败时，该方法被调用（成功，则不调用）
 * 需要注意的是：该方法调用后，MsgSendConfirmCallBack中的confirm方法也会被调用
 * @创建人: CM
 * @创建时间: 2019-1-18
 */
public class MsgReturnCallBack implements RabbitTemplate.ReturnCallback {
    @Override
    public void returnedMessage(Message message, int i, String s, String s1, String s2) {
        System.out.println("MsgSendReturnCallBack[消息从交换机到队列失败] message:"+message);
        // TODO: 2019-1-18 消息从交换机到队列失败，重新发送
    }
}
