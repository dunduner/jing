package com.jing.study.annotation;

import java.lang.annotation.*;

/**
 * @author zhangning
 * @date 2020/10/19
 * 我是定义在成员变量(字段)上的注解
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserFieldAnnotation {

    String value() default "我是定义在成员变量(字段)上的  -----的默认值";

}
