package com.jing.study.rocketmq.mashibing.transation;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.List;

/**
 * @author ning11.zhang
 * @Description:
 * @date 2021/2/7
 */
public class A1_Consumer {


    public static void main(String[] args)  throws  Exception{
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("tr-consumer-ZN-one");
        String nameServer = "localhost:9876";
        consumer.setNamesrvAddr(nameServer);
        consumer.subscribe("tr_messages_new1", "*");


        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> messages, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
                try {
                    for (MessageExt message : messages) {
                        System.out.println("事务消息-消费的："+new String(message.getBody()));
                    }
                    return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
                }catch (Exception e){
                    return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }

            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.println("Consumer Started.01--------------");
    }
}
