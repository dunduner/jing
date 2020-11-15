package com.jing.study.annotation;


import java.lang.annotation.*;


/**
 * 定义在参数上的注解
 */
@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface UserParamAnnotation {
    /**
     * 定义注解的一个元素 并给定默认值
     * @return
     */
    String value() default "我是定义在参数上的注解元素value的默认值";

}
