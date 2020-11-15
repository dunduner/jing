package com.jing.study.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * @author zhangning
 * @date 2020/8/24
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Component
public class QueryVo<T> {
    private T object;
    private String dangqianye;
    private String size;
    private String begin;
    private String end;

    private Integer firstResult;
    private Integer maxResults;



}
