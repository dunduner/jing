package com.jing.study.job;

import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangning
 * @date 2020/10/31
 */

public class TestJob implements Job {
    @Override
    public void execute(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        //任务调度ID
        String work = jobExecutionContext.getJobDetail().getKey().getName();
        System.out.println("任务调度ID-----work:" + work);

        try {
            //定时器任务start
            System.out.println(jobExecutionContext.getJobDetail().getJobDataMap().get("name"));
            System.out.println(jobExecutionContext.getJobDetail().getJobDataMap().get("age"));
            System.out.println(jobExecutionContext.getTrigger().getJobDataMap().get("orderNo"));
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            System.out.println("定时任务执行，当前时间：" + simpleDateFormat.format(new Date()));
            System.out.println("=========================");
            //定时器任务end
        } catch (Exception e) {
            System.out.println("异常了---啦啦啦啦");
            JobExecutionException e2 = new JobExecutionException(e);
            // this job will refire immediately
            e2.refireImmediately();
            throw e2;

        }

    }

}
