package com.jing.study.sharding.oacenter;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author zhangning
 * @date 2020/8/27
 */

@Slf4j
public class OACenterShardingTableAlgorithm implements PreciseShardingAlgorithm<Long> {

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<Long> shardingValue) {
        System.out.println("+++++++++master进去根据名字name_hase分表策略+++++++++");
        System.out.println("availableTargetNames:"+ JSON.toJSONString(availableTargetNames));
        System.out.println("shardingValue:"+JSON.toJSONString(shardingValue));
        System.out.println("---------------------------------------------");

        Object value = shardingValue.getValue();
        int hashCode = value.hashCode();
        int index = Math.abs(hashCode % 10);
        System.out.println("表的索引值:"+index);
        String tableName = availableTargetNames.toArray(ArrayUtils.EMPTY_STRING_ARRAY)[index];
        //按月路由
        for (String each : availableTargetNames) {
            System.out.println("++++++++++++");
            System.out.println(each);
            //1 2 3 4 5..12
            if (each.endsWith(String.valueOf(index))) {
                //这里返回回去的就是最终需要查询的表名
                return each;
            }
        }
        return tableName;
    }
}
