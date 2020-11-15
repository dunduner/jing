package com.jing.study.util;

import com.alibaba.fastjson.JSON;
import org.apache.commons.lang3.StringUtils;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingAlgorithm;
import org.apache.shardingsphere.api.sharding.standard.PreciseShardingValue;

import java.util.Collection;

/**
 * @author zhangning
 * @date 2020/8/27
 */
public class SecondDatabaseStrategy implements PreciseShardingAlgorithm<String> {
    @Override
    public String doSharding(Collection<String> databaseNames, PreciseShardingValue<String> preciseShardingValue) {
        //对于库的分片collection存放的是所有的库的列表，这里代表test_2019~dataSource_2021
        System.out.println("+++++++++1/master 精准模型数据 库 分片算法+++++++++");
        System.out.println("databaseNames:" + JSON.toJSONString(databaseNames));
        //{"columnName":"create_time","logicTableName":"tab_user","value":"2019-02-02 23:23:23"}
        System.out.println("preciseShardingValue:" + JSON.toJSONString(preciseShardingValue));

        //配置的分片的sharding-column对应的值
//        取到分表分库逻辑中的列的值
        String timeValue = preciseShardingValue.getValue();
        //分库时配置的sharding-column
        String time = preciseShardingValue.getColumnName();
        //需要分库的逻辑表
        String table = preciseShardingValue.getLogicTableName();
        if (StringUtils.isBlank(timeValue)) {
            throw new UnsupportedOperationException("preciseShardingValue is null");
        }
        //按年路由
        for (String eachDatabase : databaseNames) {
            String value = StringUtils.substring(timeValue, 0, 4); //获取到年份
            System.out.println("master选中的库：" + eachDatabase);
            //主库只有test
            return eachDatabase;
        }
        throw new UnsupportedOperationException();
    }
}
