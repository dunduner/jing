package com.jing.study.rocketmq.延时消息样例;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;

import java.text.SimpleDateFormat;
import java.util.Date;


//发送延时消息
public class ScheduledMessageProducer {
    public static void main(String[] args) throws Exception {
        // 实例化一个生产者来产生延时消息
        DefaultMQProducer producer = new DefaultMQProducer("scheduled_producer");
        producer.setNamesrvAddr("127.0.0.1:9876");
        producer.start();        // 启动生产者
        int totalMessagesToSend = 5;

        Date date = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateStr = sdf.format(date);//当前时间的字符串

        for (int i = 0; i < totalMessagesToSend; i++) {
            Message message = new Message("TopicTest", "tag-first",(dateStr+"|Hello scheduled message " + i).getBytes());
            // 设置延时等级3,这个消息将在10s之后发送(现在只支持固定的几个时间,详看delayTimeLevel)
            message.setDelayTimeLevel(3);
            //private String messageDelayLevel = "1s 5s 10s 30s 1m 2m 3m 4m 5m 6m 7m 8m 9m 10m 20m 30m 1h 2h";
            //发送消息
            producer.send(message);
        }
        // 关闭生产者
        producer.shutdown();
    }
}