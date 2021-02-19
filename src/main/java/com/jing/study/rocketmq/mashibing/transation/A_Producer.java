package com.jing.study.rocketmq.mashibing.transation;

import org.apache.rocketmq.client.producer.LocalTransactionState;
import org.apache.rocketmq.client.producer.TransactionListener;
import org.apache.rocketmq.client.producer.TransactionMQProducer;
import org.apache.rocketmq.client.producer.TransactionSendResult;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;

import java.util.concurrent.TimeUnit;

/**
 * @author ning11.zhang
 * @Description:
 * @date 2021/2/7
 */
public class A_Producer {
    public static void main(String[] args) throws Exception {

        TransactionMQProducer transactionMQProducer = new TransactionMQProducer("TR_MQ_ZN_NEW1");
        transactionMQProducer.setNamesrvAddr("localhost:9876");
        transactionMQProducer.setTransactionListener(new TransactionListener() {
            @Override
            public LocalTransactionState executeLocalTransaction(Message msg, Object arg) {
                System.out.println("execute:id==="+msg.getTransactionId());
                System.out.println("execute:body=="+new String(msg.getBody()));
                System.out.println("execute:arg=="+arg);
                try {
                    TimeUnit.SECONDS.sleep(5);
                    System.out.println("commit---");
                    return LocalTransactionState.COMMIT_MESSAGE;
                }catch (Exception ew){
                    return LocalTransactionState.ROLLBACK_MESSAGE;
                }
            }

            @Override
            public LocalTransactionState checkLocalTransaction(MessageExt msg) {
                System.out.println("check:id==="+msg.getTransactionId());
                System.out.println("check:body=="+new String(msg.getBody()));
                return LocalTransactionState.COMMIT_MESSAGE;
            }
        });
        transactionMQProducer.start();


        TransactionSendResult transactionSendResult =
                transactionMQProducer.sendMessageInTransaction(
                        new Message("tr_messages_new1", "事务消息aaa".getBytes()), "arg_value");
        System.out.println(transactionSendResult);
        transactionMQProducer.shutdown();
    }
}
