package com.jing.study.job;

import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * @author zhangning
 * @date 2020/9/1
 */


@Component
@EnableScheduling
public class PTJobTest {

    //10秒后 一分钟一次
    @Scheduled(cron = "10/59 * * * * ?")
    public void sendMsg1930() {
        System.out.println("10秒后 一分钟一次："+this.getClass().getName()+"，执行时间:"+new Date().toString());
    }
}

