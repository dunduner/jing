package com.jing.study.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author zhangning
 * @date 2020/8/20
 * <p>
 * 精准模型“表”分片算法
 * 3year_12month
 */
public class PreciseModuloTableShardingAlgorithm implements PreciseShardingAlgorithm<String>{
        @Override
        public String doSharding(Collection<String> tables, PreciseShardingValue<String> preciseShardingValue) {
            System.out.println("+++++++++2/精准模型“表”分片算法+++++++++");
            System.out.println("TableNames:"+ JSON.toJSONString(tables));
            System.out.println("preciseShardingValue:"+JSON.toJSONString(preciseShardingValue));
            //对于库的分片collection存放的是所有的库的列表，这里代表flow_01~flow_12
            //配置的分片的sharding-column对应的值
            String timeValue = preciseShardingValue.getValue();
            //判断timeValue是否为空
            if(StringUtils.isBlank(timeValue)){
                System.out.println("prec为空值");
                throw new UnsupportedOperationException("prec is null");
            }
            String value = StringUtils.substring(timeValue, 5,7); //获取到月份 01 07  09 11 12等
            int month = Integer.parseInt(value);
            if(month<10){
                value = StringUtils.substring(value,1);
            }
            System.out.println("timeValue:"+timeValue);
            System.out.println("value:"+value);
            //按月路由
            for (String each : tables) {
                System.out.println("表的each是："+each);
                //1 2 3 4 5..12
                if (each.endsWith(value)) {
                    //这里返回回去的就是最终需要查询的表名
                    return each;
                }
            }
            return null;
        }

        public static void main(String[] args) {
            String value = StringUtils.substring("tab_user12", 8); //获取到月份
            System.out.println(value);
        }
}
