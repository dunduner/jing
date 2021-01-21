package com.jing.study.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * @author zhangning
 * @date 2020/8/25
 * 给出年月的起点和重点，拿到所有年月的集合
 */
public class MonthQueryUtil {

    /**
     * 给出年月的起点和重点，拿到所有年月的集合
     *
     * @param begin
     * @param end
     * @return
     */
    public ArrayList<String> getYearAndMonthList(String begin, String end) {
        ArrayList<String> getYearAndMonthList = new ArrayList<>();
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
            Date d1 = new SimpleDateFormat("yyyy-MM").parse(begin);//定义起始日期
            Date d2 = new SimpleDateFormat("yyyy-MM").parse(end);//定义结束bai日期
            Calendar dd = Calendar.getInstance();//定义日期实例
            dd.setTime(d1);//设置日期起始时间
            while (dd.getTime().before(d2)) {//判断是否到结束日期
                String str = sdf.format(dd.getTime());
                getYearAndMonthList.add(str);
                dd.add(Calendar.MONTH, 1);//进行当前日期月份加1
            }
            getYearAndMonthList.add(sdf.format(d2.getTime()));
        } catch (Exception e) {
            System.out.println("异常" + e.getMessage());
        }
        return getYearAndMonthList;
    }

    public static void main(String[] args) {
        MonthQueryUtil monthQueryUtil = new MonthQueryUtil();
//        表lowerEndpoint:2019-11-01
//        表upperEndpoint:2020-03-31
        ArrayList<String> yearAndMonthList = monthQueryUtil.getYearAndMonthList("2010-11-11", "2020-03-22");
        System.out.println(yearAndMonthList.toString());
    }
}
