package com.jing.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author zhangning
 * @date 2020/8/17
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class ResponseUser {

    private int code;
    private String message;
    private  Object data;

    //设置所有
    public void setAll(int code,String message,Object data){
        this.code = code;
        this.message = message;
        this.data = data;
    }
}
