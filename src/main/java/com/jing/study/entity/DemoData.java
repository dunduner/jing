package com.jing.study.entity;

import com.alibaba.excel.annotation.ExcelIgnore;
import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

import java.util.Date;

/**
 * @author zhangning
 * @date 2020/11/2
 */
@Data
public class DemoData {
    @ExcelProperty({"主标题", "字符串标题"})
    private String string;
    @ExcelProperty({"主标题", "日期标题"})
    private Date date;
    @ExcelProperty({"主标题", "数字标题"})
    private Double doubleData;
    /**
     * 忽略这个字段
     */
    @ExcelIgnore
//    @ExcelProperty("忽略字段")
    private String ignore;
}