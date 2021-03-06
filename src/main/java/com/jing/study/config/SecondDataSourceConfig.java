package com.jing.study.config;

import com.jing.study.sharding.rang.RangeModuloDatabaseShardingAlgorithm;
import com.jing.study.sharding.rang.RangeModuloTableShardingAlgorithm;
import com.jing.study.util.PreciseModuloDatabaseShardingAlgorithm;
import com.jing.study.util.PreciseModuloTableShardingAlgorithm;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.shardingsphere.api.config.sharding.ShardingRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.TableRuleConfiguration;
import org.apache.shardingsphere.api.config.sharding.strategy.StandardShardingStrategyConfiguration;
import org.apache.shardingsphere.shardingjdbc.api.ShardingDataSourceFactory;
import org.apache.shardingsphere.shardingjdbc.spring.boot.util.DataSourceUtil;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @author zhangning
 * @date 2020/8/27
 * 数据源二---数据源二
 * 数据源二
 * 数据源二
 */
@Configuration
@MapperScan(basePackages = {"com.jing.study.dao2"}, sqlSessionFactoryRef = "secondSqlSessionFactory")
public class SecondDataSourceConfig {
    // 精确到目录，以便跟其他数据源隔离
    //test2019
    @Value("${spring.shardingsphere.datasource.test2019.driver-class-name}")
    private String driver2019;
    @Value("${spring.shardingsphere.datasource.test2019.jdbc-url}")
    private String url2019;
    @Value("${spring.shardingsphere.datasource.test2019.username}")
    private String username2019;
    @Value("${spring.shardingsphere.datasource.test2019.password}")
    private String password2019;

    //test2020
    @Value("${spring.shardingsphere.datasource.test2020.driver-class-name}")
    private String driver2020;
    @Value("${spring.shardingsphere.datasource.test2020.jdbc-url}")
    private String url2020;
    @Value("${spring.shardingsphere.datasource.test2020.username}")
    private String username2020;
    @Value("${spring.shardingsphere.datasource.test2020.password}")
    private String password2020;


    //test2021
    @Value("${spring.shardingsphere.datasource.test2021.driver-class-name}")
    private String driver2021;
    @Value("${spring.shardingsphere.datasource.test2021.jdbc-url}")
    private String url2021;
    @Value("${spring.shardingsphere.datasource.test2021.username}")
    private String username2021;
    @Value("${spring.shardingsphere.datasource.test2021.password}")
    private String password2021;


    /**
     * 创建sharding-jdbc的DataSource实例
     *
     * @return
     */
    @Bean(name = "secondDataSource")
    @Qualifier("secondDataSource")
    public DataSource shardingDataSource() {
//        return DataSourceBuilder.create().build();
        return getShardingDataSource();
    }


    @Bean(name = "secondSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("secondDataSource") DataSource secondDataSource)
            throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(secondDataSource);//设置第二个数据源
//        sessionFactory.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("com.jing.study.dao"));
        SqlSessionFactory sqlSessionFactory = sessionFactory.getObject();
        return sqlSessionFactory;
    }

    /**
     * 获取sharding的数据源
     *
     * @return
     */
    DataSource getShardingDataSource() {
        ShardingRuleConfiguration shardingRuleConfig = new ShardingRuleConfiguration();
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfigurationMaster());//添加分表逻辑
//        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfiguration201920202021());//添加分表逻辑
        shardingRuleConfig.getBindingTableGroups().add("tab_user");//表前缀
        //数据库分库逻辑
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("create_time", new PreciseModuloDatabaseShardingAlgorithm(), new RangeModuloDatabaseShardingAlgorithm()));
        //表逻辑  标准分片 可以传2个参数   ---参数 分表算法实体类
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("create_time", new PreciseModuloTableShardingAlgorithm(), new RangeModuloTableShardingAlgorithm()));
        Properties props = new Properties();
        props.put("sql.show", "true");
        try {
            //分库分表的 数据源map,  这里可以添加多个数据源
            return ShardingDataSourceFactory.createDataSource(createDataSourceMap(), shardingRuleConfig, props);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    //雪花算法key  未用
//    private static KeyGeneratorConfiguration getKeyGeneratorConfiguration() {
//        KeyGeneratorConfiguration result = new KeyGeneratorConfiguration("SNOWFLAKE", "idxxxx");
//        return result;
//    }
    //分表逻辑
    TableRuleConfiguration getOrderTableRuleConfigurationMaster() {
        TableRuleConfiguration result = new TableRuleConfiguration("tab_user", "test$->{2019..2021}.tab_user$->{1..12}");
        //result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());//设置雪花算法的id
        return result;
    }

    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource")
        //***利用这个方法添加多个数据库
    Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        // 配置真实数据源 test2019
        Map<String, Object> dataSourceProperties2019 = new HashMap<>();
        dataSourceProperties2019.put("DriverClassName", driver2019);
        dataSourceProperties2019.put("jdbcUrl", url2019);
        dataSourceProperties2019.put("username", username2019);
        dataSourceProperties2019.put("password", password2019);
        // 配置真实数据源 test2020
        Map<String, Object> dataSourceProperties2020 = new HashMap<>();
        dataSourceProperties2020.put("DriverClassName", driver2020);
        dataSourceProperties2020.put("jdbcUrl", url2020);
        dataSourceProperties2020.put("username", username2020);
        dataSourceProperties2020.put("password", password2020);
        // 配置真实数据源 test2021
        Map<String, Object> dataSourceProperties2021 = new HashMap<>();
        dataSourceProperties2021.put("DriverClassName", driver2021);
        dataSourceProperties2021.put("jdbcUrl", url2021);
        dataSourceProperties2021.put("username", username2021);
        dataSourceProperties2021.put("password", password2021);

        try {
            DataSource ds2019 = DataSourceUtil.getDataSource("com.zaxxer.hikari.HikariDataSource", dataSourceProperties2019);
            DataSource ds2020 = DataSourceUtil.getDataSource("com.zaxxer.hikari.HikariDataSource", dataSourceProperties2020);
            DataSource ds2021 = DataSourceUtil.getDataSource("com.zaxxer.hikari.HikariDataSource", dataSourceProperties2021);
            result.put("test2019", ds2019);
            result.put("test2020", ds2020);
            result.put("test2021", ds2021);

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return result;
    }
}
