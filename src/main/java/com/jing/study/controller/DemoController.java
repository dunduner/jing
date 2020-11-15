package com.jing.study.controller;

import com.jing.study.annotation.OperLog;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zhangning
 * @date 2020/10/19
 */

@RestController
@RequestMapping("/hello")
public class DemoController {

    private static Logger logger = LoggerFactory.getLogger(DemoController.class);

    @Value("${server.port}")
    private String port;

    @Value("${stu_name}")
    private String stu_name;

    @Value("${hello}")
    private String hw;

    //日志AOP
    @OperLog(operModul = "helloworld-zn", operType = "controller")
    @GetMapping("/world")
    public String hello() {
        logger.debug( "debug log..." );
        logger.info( "info log..." );
        logger.warn( "warn log..." );
        System.out.println(hw);
        return "hello world！服务的port：" + port+",stuname:"+stu_name;
    }

    @OperLog(operModul = "helloJson-zn", operType = "controller-helloJson")
    @GetMapping("/json")
    public String helloJson(@PathVariable String name) {
        return "hello world:" + name;
    }


}
