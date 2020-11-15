package com.jing.study.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.DecimalFormat;

/**
 * @author zhangning
 * @date 2020/7/14
 */


@RequestMapping("/dingdan")
@RestController
public class TingdanController {

    //produces = "text/event-stream;charset=UTF-8"一定要带上
    @GetMapping(value = "/get_data/{driverId}", produces = "text/event-stream;charset=UTF-8")
    public String push(@PathVariable String driverId) {
//        try {
//            Thread.sleep(1000);
//        } catch (Exception e) {
//            e.printStackTrace();
//        }

        double moeny = Math.random() * 10;
        System.out.println(String.format("%.2f", moeny));
        DecimalFormat df = new DecimalFormat(".00");
        String canshu = df.format(moeny);
        //！！！注意，EventSource返回的参数必须以data:开头，"\n\n"结尾，不然onmessage方法无法执行。
        return "data:白菜id为["+driverId+"]价格行情:" + canshu + "元" + "\n\n";
    }
}
