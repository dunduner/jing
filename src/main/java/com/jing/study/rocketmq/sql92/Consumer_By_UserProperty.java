package com.jing.study.rocketmq.sql92;

import lombok.SneakyThrows;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.MessageSelector;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.List;

/**
 * @author ning11.zhang
 * @Description:
 * @date 2021/1/4
 */
public class Consumer_By_UserProperty {

    public static void main(String[] args) throws InterruptedException, MQClientException {
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer();     // 实例化消费者
        consumer.setConsumerGroup("putUserProperty");//设置消费组
        consumer.setNamesrvAddr("localhost:9876");//多个  "10.10.12.203:9876;10.10.12.204:9876"
        // 只有订阅的消息有这个属性a, price >=0 and price <= 3
        consumer.subscribe("RMQ_A2B_XXX_ORDER_DEV", MessageSelector.bySql("price between 4 and 9"));

        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @SneakyThrows
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
                for (MessageExt messageExt : msgs) {
//                        System.out.println("MsgId:" + messageExt.getMsgId());
//                        System.out.println("Topic:" + messageExt.getTopic());
                        String body = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        System.out.println("consumeThread=" + Thread.currentThread().getName() + "|||body:" + body);
//                        System.out.println("CommitLogOffset:"+messageExt.getCommitLogOffset());
                        System.out.println("QueueId:" + messageExt.getQueueId());
                        System.out.println("QueueOffset:" + messageExt.getQueueOffset());
                        System.out.println("Keys:" + messageExt.getKeys());
                        System.out.println("-------------------");
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                }
                // 标记该消息已经被成功消费
                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
            }
        });
        // 启动消费者实例
        consumer.start();
        System.out.println("Consumer Started.%n--------------");
    }
}
