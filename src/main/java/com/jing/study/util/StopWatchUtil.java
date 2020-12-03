package com.jing.study.util;

import org.springframework.util.StopWatch;

/**
 * @author ning11.zhang
 * @Description:
 * @date 2020/11/16
 */
public class StopWatchUtil {
    private void test() throws InterruptedException {
        StopWatch sw = new StopWatch();

        sw.start("起床");
        Thread.sleep(1000);
        sw.stop();

        sw.start("洗漱");
        Thread.sleep(2000);
        sw.stop();

        sw.start("锁门");
        Thread.sleep(500);
        sw.stop();

        System.out.println(sw.prettyPrint());
        System.out.println(sw.getTotalTimeMillis());//总花费时间
        System.out.println(sw.getLastTaskName());//最后一个任务
        System.out.println(sw.getLastTaskInfo());
        System.out.println(sw.getTaskCount());//任务总数
        /*一个start和一个stop就是一个任务*/
    }


    public static void main(String[] argv) throws InterruptedException {
        StopWatchUtil testStopWatch = new StopWatchUtil();
        testStopWatch.test();

    }
}
