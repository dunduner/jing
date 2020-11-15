package com.jing.study.sharding.rang;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Range;
import com.jing.study.util.MonthQueryUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zhangning
 * @date 2020/8/24
 */
public class RangeModuloTableShardingAlgorithm implements RangeShardingAlgorithm<String> {
    @Override
    public Collection<String> doSharding(Collection<String> rangeList, RangeShardingValue<String> rangeShardingValue) {

        System.out.println("+++++++++进去范围“选表”策略+++++++++");
//        System.out.println("rangeList:" + JSON.toJSONString(rangeList));
//        System.out.println("rangeShardingValue:" + JSON.toJSONString(rangeShardingValue));
//        System.out.println("---------------------------------------------");
        Collection<String> collect = new ArrayList<>();

        Range<String> valueRange = rangeShardingValue.getValueRange();
        String lowerEndpoint = valueRange.lowerEndpoint();
        String upperEndpoint = valueRange.upperEndpoint();
//        System.out.println("表lowerEndpoint:" + lowerEndpoint);
//        System.out.println("表upperEndpoint:" + upperEndpoint);
        //取到月份
//        String lowerValue = StringUtils.substring(lowerEndpoint, 5, 7); //获取到月份 01 07  09 11 12等
//        String upperValue = StringUtils.substring(upperEndpoint, 5, 7); //获取到月份 01 07  09 11 12等
        //拿到所有要查询的年月
        MonthQueryUtil monthQueryUtil = new MonthQueryUtil();

        ArrayList<String> yearAndMonthList = monthQueryUtil.getYearAndMonthList(lowerEndpoint, upperEndpoint);

        for (String tableseach : rangeList) {
            //此处的8  是根据表名长度决定的
            String substringMonth = StringUtils.substring(tableseach, 8);//获取到月份
            if(Integer.parseInt(substringMonth)<10){
                substringMonth = "0"+substringMonth;
            }
            for (String yearAndMonth : yearAndMonthList) {
                String substring = yearAndMonth.substring(5);
                if(substring.equals(substringMonth)){
                    //解决重复放
                    if(!collect.contains(tableseach)){
                        collect.add(tableseach);
                    }
                }
            }
        }
        System.out.println("table_collect:" + JSON.toJSONString(collect));
        return collect;
    }
}
