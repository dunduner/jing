package com.jing.study.entity.people;

import com.jing.study.annotation.UserAnnotation;
import com.jing.study.annotation.UserFieldAnnotation;
import com.jing.study.annotation.UserMethodAnnotation;
import com.jing.study.annotation.UserParamAnnotation;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author zhangning
 * @date 2020/7/11
 */
@UserAnnotation(name = "wangwu")
public class Ren implements BeanPostProcessor {
    @UserFieldAnnotation
    private String field = "我是字段";

    @UserMethodAnnotation
    public void hello(@UserParamAnnotation("haha") String name) {
        System.out.println("helloMethod的参数是："+name);
    }

    public static void main(String[] args) throws  Exception {

        Method[] methods = Ren.class.getMethods();
        for (int i = 0; i < methods.length; i++) {
//            System.out.println(methods[i]);
        }

        Method hello = Class.forName("com.study.demo.people.Ren").getMethod("hello", String.class);
        UserMethodAnnotation annotation = hello.getAnnotation(UserMethodAnnotation.class);

        System.out.println(annotation.value());
        System.out.println(annotation.sata());

        Annotation[][] annotations = hello.getParameterAnnotations();
        for (Annotation[] tt : annotations) {
            for (Annotation t1 : tt) {
                System.out.println("参数上的注解值 === " + ((UserParamAnnotation) t1).value());
//                if(t1 instanceof MyAnTargetParameter){
//                    System.out.println("参数上的注解值 === "+((MyAnTargetParameter) t1).value());
//                }
            }
        }
        //调用hello方法，改变内部参数
        hello.invoke(new Ren(), "调用hello方法，改变内部参数---改变默认参数zzz");
        Field field = Class.forName("com.study.demo.people.Ren").getDeclaredField("field");

        System.out.println(field.getDeclaredAnnotation(UserFieldAnnotation.class).value());

    }

    private String name;
    private boolean sex;//ture 男， false 女
    private int age;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getSex() {
        return sex;
    }

    public void setSex(boolean sex) {
        this.sex = sex;
    }


    @Override
    public String toString() {
        return "Ren{" +
                "name='" + name + '\'' +
                ", sex=" + sex +
                ", age=" + age +
                '}';
    }


    public Ren(String name, boolean sex, int age) {
        this.name = name;
        this.sex = sex;
        this.age = age;
    }


    public Ren() {
        System.out.println("我是无参构造");
    }

    public Ren(String name) {
        this.name = name;
    }

    @Override
    public Object postProcessBeforeInitialization(Object o, String s) throws BeansException {
        return null;
    }

    @Override
    public Object postProcessAfterInitialization(Object o, String s) throws BeansException {
        return null;
    }
}
