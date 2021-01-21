package com.jing.study.rocketmq.producer;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

/**
 * @author ning11.zhang
 * @Description:
 * @date 2021/1/4
 *
 *  同步消息
 * 这种可靠性同步地发送方式使用的比较广泛，比如：重要的消息通知，短信通知。
 * 发送顺序是对的
 */
public class SyncProducer {
    public static void main(String[] args) throws Exception {
        // 实例化消息生产者Producer
        DefaultMQProducer producer = new DefaultMQProducer("zhangning_tongbu");
        // 设置NameServer的地址
        producer.setNamesrvAddr("localhost:9876");
        // 启动Producer实例
        producer.start();
        int num = 0;
        for (int i = 0; i < 5; i++) {
            // 创建消息，并指定Topic，Tag和消息体
            Message msg = new Message("TopicTest" /* Topic */,
                    "Tag_tongbu" /* Tag */,
                    ("同步 RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET) /* Message body 用默认的UTF-8*/
            );
            // 发送消息到一个Broker
            SendResult sendResult = producer.send(msg);
            // 通过sendResult返回消息是否成功送达
            System.out.printf("%s%n", sendResult+",");
            num++;
        }
        System.out.println("共发送："+num+"条");
        // 如果不再发送消息，关闭Producer实例。
        producer.shutdown();
    }
}
