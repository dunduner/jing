package com.jing.study.rocketmq.consumer;

import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.exception.MQClientException;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * @author ning11.zhang
 * @Description:
 * @date 2021/1/4
 *
 * 消费tags 是衣服的消费者
 */
public class Consumer_order_clothes {

    public static void main(String[] args) throws InterruptedException, MQClientException {
        // 实例化消费者
        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer("zhangning_tongbu_consumer_clothes");
        // 设置NameServer的地址
        String nameServer = "localhost:9876";
        consumer.setNamesrvAddr(nameServer);

        // 订阅一个或者多个Topic，以及Tag来过滤需要消费的消息
        consumer.subscribe("RMQ_A2B_XXX_ORDER_DEV", "tags_clothes");
        // 注册回调实现类来处理从broker拉取回来的消息
        consumer.registerMessageListener(new MessageListenerConcurrently() {
            @Override
            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> msgs, ConsumeConcurrentlyContext context) {
//                System.out.printf("%s Receive New Messages: %s %n", Thread.currentThread().getName(), msgs);
                for (MessageExt messageExt : msgs) {
                    try {
//                        System.out.println("MsgId:" + messageExt.getMsgId());
//                        System.out.println("Topic:" + messageExt.getTopic());
                        String body = new String(messageExt.getBody(), RemotingHelper.DEFAULT_CHARSET);
                        System.out.println("consumeThread=" + Thread.currentThread().getName()+"|||body:" + body);
//                        System.out.println("CommitLogOffset:"+messageExt.getCommitLogOffset());
                        System.out.println("QueueId:"+messageExt.getQueueId());
                        System.out.println("QueueOffset:"+messageExt.getQueueOffset());
                        System.out.println("Keys:"+messageExt.getKeys());
                        System.out.println("-------------------");
                    } catch (UnsupportedEncodingException e) {
                        e.printStackTrace();
                        return ConsumeConcurrentlyStatus.RECONSUME_LATER;
                    }
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
