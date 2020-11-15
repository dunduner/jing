package com.jing.study.annotation;


import java.lang.annotation.*;


/**
 * 类上的注解
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserAnnotation {
    String name() default "张三";
}
