package com.example.demo.config;

import com.example.demo.mqcallback.MsgReturnCallBack;
import com.example.demo.mqcallback.MsgSendConfirmCallBack;
import com.rabbitmq.client.Channel;
import com.sun.org.apache.regexp.internal.RE;
import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @version 1.0
 * @类名: RabbitMqConfig.java
 * @描述: RabbitMq配置
 * @创建人: CM
 * @创建时间: 2019-1-18
 */
@Configuration
public class RabbitMqConfig {
    /*消息交换机的名字*/
    public static final String EXCHANGE = "exchangeTest";
    /*队列key1*/
    public static final String ROUTINGKEY1 = "queue_one_key1";
    /*队列key2*/
    public static final String ROUTINGKEY2 = "queue_one_key2";

    @Autowired
    private ExchangeConfig exchangeConfig;
    @Autowired
    private QueueConfig queueConfig;
    @Autowired
    /*连接工厂*/
    private ConnectionFactory connectionFactory;

    /**
     * 将消息队列1和交换机进行绑定
     * 时间:   2019-1-18 14:49
     * 作者:   CM
     *
     * @param
     * @return org.springframework.amqp.core.Binding
     */
    @Bean
    public Binding bingding_one() {
        return BindingBuilder.bind(queueConfig.firstQueue()).to(exchangeConfig.directExchange()).with(ROUTINGKEY1);
    }

    /**
     * 将消息队列2和交换机进行绑定
     * 时间:   2019-1-18 14:49
     * 作者:   CM
     *
     * @param
     * @return org.springframework.amqp.core.Binding
     */
    @Bean
    public Binding bingding_tow() {
        return BindingBuilder.bind(queueConfig.secondQueue()).to(exchangeConfig.directExchange()).with(ROUTINGKEY2);
    }

    /**
     * queue listener 观察监听模式
     * 当有消息到达时就会通知监听在对应队列上的监听对象
     * 时间:   2019-1-18 15:01
     * 作者:   CM
     *
     * @param
     * @return org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer
     */
    /*@Bean
    public SimpleMessageListenerContainer simpleMessageListenerContainer_one() {
        SimpleMessageListenerContainer simpleMessageListenerContainer = new SimpleMessageListenerContainer(connectionFactory);
        simpleMessageListenerContainer.addQueues(queueConfig.firstQueue());
        simpleMessageListenerContainer.setExposeListenerChannel(true);
        simpleMessageListenerContainer.setMaxConcurrentConsumers(1);
        simpleMessageListenerContainer.setConcurrentConsumers(1);
        simpleMessageListenerContainer.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        simpleMessageListenerContainer.setMessageListener(new ChannelAwareMessageListener() {
            @Override
            public void onMessage(Message message, Channel channel) throws Exception {
                channel.basicQos(1);
                byte[] body = message.getBody();
                System.out.println("接收队列的消息："+new String(body));
                channel.basicAck(message.getMessageProperties().getDeliveryTag(),false);
            }
        });
        return simpleMessageListenerContainer;
    }*/

    /**
     * 定义rabbit template 用于数据的接收和发送
     * 时间:   2019-1-18 15:10
     * 作者:   CM
     *
     * @param
     * @return org.springframework.amqp.rabbit.core.RabbitTemplate
     */
    @Bean
    public RabbitTemplate rabbitTemplate() {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        //消息发送到交换机的回调
        /**若使用confirm-callback或return-callback，
         * 必须要配置publisherConfirms或publisherReturns为true
         * 每个rabbitTemplate只能有一个confirm-callback和return-callback
         */
        template.setConfirmCallback(msgSendConfirmCallBack());
        template.setReturnCallback(msgReturnCallBack());
        /**
         * 使用return-callback时必须设置mandatory为true，或者在配置中设置mandatory-expression的值为true，
         * 可针对每次请求的消息去确定’mandatory’的boolean值，
         * 只能在提供’return -callback’时使用，与mandatory互斥
         */
        template.setMandatory(true);
        return template;
    }

    /**
     * 关于 msgSendConfirmCallBack 和 msgSendReturnCallback 的回调说明：
     * 1.如果消息没有到exchange,则confirm回调,ack=false
     * 2.如果消息到达exchange,则confirm回调,ack=true
     * 3.exchange到queue成功,则不回调return
     * 4.exchange到queue失败,则回调return(需设置mandatory=true,否则不回调,消息就丢了)
     */

    /**
     * 消息确认机制
     * Confirms给客户端一种轻量级的方式，能够跟踪哪些消息被broker处理，
     * 哪些可能因为broker宕掉或者网络失败的情况而重新发布。
     * 确认并且保证消息被送达，提供了两种方式：发布确认和事务。(两者不可同时使用)
     * 在channel为事务时，不可引入确认模式；同样channel为确认模式下，不可使用事务。
     * @return
     */

    /**
     * 消息确认机制
     * 时间:   2019-1-18 15:09
     * 作者:   CM
     *
     * @param
     * @return com.example.demo.mqcallback.MsgSendConfirmCallBack
     */
    @Bean
    public MsgSendConfirmCallBack msgSendConfirmCallBack() {
        return new MsgSendConfirmCallBack();
    }

    public MsgReturnCallBack msgReturnCallBack(){
        return new MsgReturnCallBack();
    }
}
