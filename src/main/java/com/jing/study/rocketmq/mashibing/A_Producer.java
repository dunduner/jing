package com.jing.study.rocketmq.mashibing;

import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.remoting.common.RemotingHelper;

import java.util.ArrayList;

/**
 * @author ning11.zhang
 * @Description:
 * @date 2021/2/7
 */
public class A_Producer {


    public static void main(String[] args) throws Exception {
        DefaultMQProducer defaultMQProducer = new DefaultMQProducer();
        defaultMQProducer.setProducerGroup("producer-ZN");
        String nameServer = "localhost:9876";
        defaultMQProducer.setNamesrvAddr(nameServer);
        // 启动Producer实例
        defaultMQProducer.start();

        ArrayList<Message> messages = new ArrayList<>();
        for (int i = 1; i <= 10; i++) {
            Message message = new Message("BZ-ZN-TOPIC", "ZN-02", "ZN"+i,
                    ("value:" + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
            message.putUserProperty("price", String.valueOf(i));
            messages.add(message);
        }

        defaultMQProducer.send(messages);
        System.out.println("A_Producer。。启动了");


//        defaultMQProducer.shutdown();
    }
}
