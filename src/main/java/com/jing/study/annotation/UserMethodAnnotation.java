package com.jing.study.annotation;


import java.lang.annotation.*;

/**
 * 定义在方法上的注解
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserMethodAnnotation {
    /**
     * 定义注解的一个元素 并给定默认值
     * @return
     */
    String value() default "我是定义在方法上的注解元素value的默认值";
    int sata() default 7;
}
