package com.jing.study.sharding.rang;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Range;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.RangeShardingValue;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author zhangning
 * @date 2020/8/27
 */
public class SecondDatabaseRangeStrategy implements RangeShardingAlgorithm<String> {
    @Override
    public Collection<String> doSharding(Collection<String> databaseNames, RangeShardingValue<String> rangeShardingValue) {
        System.out.println("+++++++++22222222222进去范围分片选库策略+++++++++");
//        System.out.println("databaseNames:"+ JSON.toJSONString(databaseNames));
//        System.out.println("rangeShardingValue:"+JSON.toJSONString(rangeShardingValue));
//        System.out.println("---------------------------------------------");
        Collection<String> collect = new ArrayList<>();
        Range<String> valueRange = rangeShardingValue.getValueRange();
        String lowerEndpoint = valueRange.lowerEndpoint();
        String upperEndpoint = valueRange.upperEndpoint();
//        System.out.println("数据库lowerEndpoint:"+lowerEndpoint);
//        System.out.println("数据库upperEndpoint:"+upperEndpoint);
        //取到年份
        int lowerEndpointInt = Integer.parseInt(StringUtils.substring(lowerEndpoint, 0, 4));
        int upperEndpointInt = Integer.parseInt(StringUtils.substring(upperEndpoint, 0, 4));

        for (int  yearValue = lowerEndpointInt; yearValue <= upperEndpointInt; yearValue++) {
            for (String eachdatabase : databaseNames) {
                    collect.add(eachdatabase);
            }
        }
        System.out.println("database_collect:"+ JSON.toJSONString(collect));
        return collect;
    }
}

