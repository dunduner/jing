package com.jing.study.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author zhangning
 * @date 2020/7/9
 */


@Controller
@RequestMapping("/jsp")
public class TestController {

    @Value( "${server.port}" )
    private String port;

    @RequestMapping("/test")
    public String test() {
        System.out.println("去test.jsp页面");
        System.out.println("系统的端口号是："+port);
        // 通过url无法直接访问 WEB - INF文件夹下的jsp页面
        // 需访问对应名称的controller，在controller对应的方法中返回jsp页面
        return "test";
    }


    public static void main(String[] args) {
        String loginName = "张海宁20201231";
        int hashCode = loginName.hashCode();
        int abs = Math.abs(hashCode % 10);
        System.out.println(abs);
    }
}
