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
     *
     * @param uuid
     * @param message
     * @return void
     */
    public void send(String uuid, Object message) {

        /**
         * 构建Message ,主要是使用 msgId 将 message 和 CorrelationData 关联起来。
         * 这样当消息发送到交换机失败的时候，在 MsgSendConfirmCallBack 中就可以根据
         * correlationData.getId()即 msgId,知道具体是哪个message发送失败,进而进行处理。
         */
        /*将 msgId和 message绑定*/
        Message message1 = MessageBuilder.withBody(message.toString().getBytes())
                .setContentType(MessageProperties.CONTENT_TYPE_JSON)
                .setCorrelationId(uuid).build();

        CorrelationData correlationData = new CorrelationData(uuid);
        // TODO 将 msgId 与 Message 的关系保存起来
        /**
         * 将 msgId 与 Message 的关系保存起来,例如放到缓存中.
         * 当 MsgSendReturnCallback回调时（消息从交换机到队列失败）,进行处理 {@code MsgSendReturnCallback}.
         * 当 MsgSendConfirmCallBack回调时,进行处理 {@code MsgSendConfirmCallBack}.
         * 定时检查这个绑定关系列表,如果发现一些已经超时(自己设定的超时时间)未被处理,则手动处理这些消息.
         */
        rabbitTemplate.convertAndSend(RabbitMqConfig.EXCHANGE, RabbitMqConfig.ROUTINGKEY1, message1, correlationData);
    }
}
