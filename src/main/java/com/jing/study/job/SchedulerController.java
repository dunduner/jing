package com.jing.study.job;

import org.quartz.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author zhangning
 * @date 2020/10/31
 */
@Controller
@RequestMapping("/dingshi")
public class SchedulerController {
    @Autowired
    private Scheduler scheduler;

    //http://localhost:8090/dingshi/Quartz?orderNo=007
    @GetMapping("/Quartz")
    @ResponseBody
    public Object quartz(@RequestParam("orderNo") String orderNo) throws Exception {
        Date start = new Date(System.currentTimeMillis() + 7 * 1000);//当前时间7秒之后
//        Date start = new Date();//当前时间
        /**通过JobBuilder.newJob()方法获取到当前Job的具体实现(以下均为链式调用)
         * 这里是固定Job创建，所以代码写死XXX.class
         * 如果是动态的，根据不同的类来创建Job，则 ((Job)Class.forName("com.zy.job.TestJob").newInstance()).getClass()
         * 即是 JobBuilder.newJob(((Job)Class.forName("com.zy.job.TestJob").newInstance()).getClass())
         */
        JobDetail jobDetail = JobBuilder.newJob(TestJob.class)
                /**给当前JobDetail添加参数，K V形式*/
                .usingJobData("name", "我是名字")
                /**给当前JobDetail添加参数，K V形式，链式调用，可以传入多个参数，在Job实现类中，可以通过jobExecutionContext.getJobDetail().getJobDataMap().get("age")获取值*/
                .usingJobData("age", 77)
                /**添加认证信息，有3种重写的方法，我这里是其中一种，可以查看源码看其余2种*/
//                .withIdentity(orderNo)
                .build();//执行

        //创建触发器
        Trigger trigger = TriggerBuilder.newTrigger()
                /**给当前JobDetail添加参数，K V形式，链式调用，可以传入多个参数，在Job实现类中，可以通过jobExecutionContext.getTrigger().getJobDataMap().get("orderNo")获取值*/
                .usingJobData("orderNo", orderNo)
                /**添加认证信息，有3种重写的方法，我这里是其中一种，可以查看源码看其余2种*/
                //后面暂停时候要用
                .withIdentity(orderNo)//withIdentity(String name, String group)
                /**立即生效*/
//              .startNow()
                /**开始执行时间*/
                .startAt(start)
                /**结束执行时间*/
//              .endAt(start)
                /**添加执行规则，SimpleTrigger、CronTrigger的区别主要就在这里*/
                .withSchedule(
                        SimpleScheduleBuilder.simpleSchedule()
                                /**每隔3s执行一次,和repeatForever一起启用，要不没用*/
//                                .withIntervalInSeconds(3)
                                /**一直执行，*/
//                                .repeatForever()
                )
                .build();//执行

//        CronTrigger trigger2 = TriggerBuilder.newTrigger()
//                /**给当前JobDetail添加参数，K V形式，链式调用，可以传入多个参数，在Job实现类中，可以通过jobExecutionContext.getTrigger().getJobDataMap().get("orderNo")获取值*/
//                .usingJobData("orderNo", orderNo)
//                /**添加认证信息，有3种重写的方法，我这里是其中一种，可以查看源码看其余2种*/
//                .withIdentity(orderNo)
//                /**开始执行时间*/
//                .startAt(start)
//                /**结束执行时间*/
//                .endAt(start)
//                /**添加执行规则，SimpleTrigger、CronTrigger的区别主要就在这里*/
//                .withSchedule(CronScheduleBuilder.cronSchedule("* 30 10 ? * 1/5 2018"))
//                .build();//执行

        /**添加定时任务*/
        scheduler.scheduleJob(jobDetail, trigger);
        if (!scheduler.isShutdown()) {
            /**启动*/
            scheduler.start();
            System.err.println("--------定时任务启动成功 " + new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()) + " ------------");
        }
        return "ok";
    }

    //http://localhost:8090/dingshi/pause?orderNo=007
    @PostMapping("/pause")
    @ResponseBody
    public Object shutdown(@RequestParam("orderNo") String orderNo) {
        try {
            scheduler.pauseTrigger(TriggerKey.triggerKey(orderNo));//暂停Trigger
        } catch (SchedulerException e) {
            System.out.println("暂停异常！");
            e.printStackTrace();
        } catch (Exception e) {
            System.out.println("暂停异常！");
            e.printStackTrace();
        }
        return "暂停成功！";
    }

    //http://localhost:8090/dingshi/resume?orderNo=007
    @PostMapping("/resume")
    @ResponseBody
    public Object resume(@RequestParam("orderNo") String orderNo) {
        try {
            scheduler.resumeTrigger(TriggerKey.triggerKey(orderNo));//恢复Trigger
        } catch (SchedulerException e) {
            e.printStackTrace();
            return "恢复异常！";
        }
        return "恢复成功！ok";
    }

    //http://localhost:8090/dingshi/del?orderNo=007
    @PostMapping("/del")
    @ResponseBody
    public Object del(@RequestParam("orderNo") String orderNo) throws IOException, SchedulerException {
        scheduler.pauseTrigger(TriggerKey.triggerKey(orderNo));//暂停触发器
        scheduler.unscheduleJob(TriggerKey.triggerKey(orderNo));//移除触发器
        scheduler.deleteJob(JobKey.jobKey(orderNo));//删除Job
        return "ok-del成功";
    }
}
