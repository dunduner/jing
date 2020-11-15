package com.jing.study.config;

import com.jing.study.sharding.oacenter.OACenterShardingTableAlgorithm;
import com.jing.study.sharding.rang.SecondDatabaseRangeStrategy;
import com.jing.study.sharding.rang.SecondTableRangeStrategy;
import com.jing.study.util.SecondDatabaseStrategy;
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
 * 数据源一
 * 数据源一
 */
@Configuration
@MapperScan(basePackages = {"com.jing.study.dao"}, sqlSessionFactoryRef = "masterSqlSessionFactory")
public class MasterDataSourceConfig {
    // 精确到目录，以便跟其他数据源隔离

    //testmaster
    @Value("${spring.shardingsphere.datasource.testmaster.driver-class-name}")
    private String driver;
    @Value("${spring.shardingsphere.datasource.testmaster.jdbc-url}")
    private String url;
    @Value("${spring.shardingsphere.datasource.testmaster.username}")
    private String username;
    @Value("${spring.shardingsphere.datasource.testmaster.password}")
    private String password;

    /**
     * 创建sharding-jdbc的DataSource实例
     *
     * @return
     */
    @Bean(name = "masterDataSource")
    @Qualifier("masterDataSource")
    public DataSource shardingDataSource() {
//        return DataSourceBuilder.create().build();
        return getShardingDataSource();
    }


    @Bean(name = "masterSqlSessionFactory")
    public SqlSessionFactory clusterSqlSessionFactory(@Qualifier("masterDataSource") DataSource masterDataSource) throws Exception {
        final SqlSessionFactoryBean sessionFactory = new SqlSessionFactoryBean();
        sessionFactory.setDataSource(masterDataSource);//设置master数据源
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
        shardingRuleConfig.getTableRuleConfigs().add(getOrderTableRuleConfigurationMaster());//添加表.库的 路由规则
        shardingRuleConfig.getBindingTableGroups().add("tab_user");//表前缀
        //数据库分库逻辑
        shardingRuleConfig.setDefaultDatabaseShardingStrategyConfig(new StandardShardingStrategyConfiguration("create_time", new SecondDatabaseStrategy(), new SecondDatabaseRangeStrategy()));
        //表逻辑  标准分片 可以传2个参数   ---参数 分表算法实体类
        shardingRuleConfig.setDefaultTableShardingStrategyConfig(new StandardShardingStrategyConfiguration("name", new OACenterShardingTableAlgorithm(), new SecondTableRangeStrategy()));
        Properties props=new Properties();
        props.put("sql.show", "true");
        try {
            //分库分表的 数据源map,
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
        TableRuleConfiguration result = new TableRuleConfiguration("tab_user", "testmaster.tab_user$->{1..12}");
        //result.setKeyGeneratorConfig(getKeyGeneratorConfiguration());//设置雪花算法的id
        return result;
    }

    @ConfigurationProperties(prefix = "spring.shardingsphere.datasource.testmaster")
    Map<String, DataSource> createDataSourceMap() {
        Map<String, DataSource> result = new HashMap<>();
        // 配置真实数据源 testmast
        Map<String, Object> dataSourceProperties = new HashMap<>();
        dataSourceProperties.put("DriverClassName", driver);
        dataSourceProperties.put("jdbcUrl", url);
        dataSourceProperties.put("username", username);
        dataSourceProperties.put("password", password);

//        // 配置真实数据源 test2019
//        Map<String, Object> dataSourceProperties2019 = new HashMap<>();
//        dataSourceProperties.put("DriverClassName", driver2019);
//        dataSourceProperties.put("jdbcUrl", url2019);
//        dataSourceProperties.put("username", username2019);
//        dataSourceProperties.put("password", password2019);
//        // 配置真实数据源 test2020
//        Map<String, Object> dataSourceProperties2020 = new HashMap<>();
//        dataSourceProperties.put("DriverClassName", driver2020);
//        dataSourceProperties.put("jdbcUrl", url2020);
//        dataSourceProperties.put("username", username2020);
//        dataSourceProperties.put("password", password2020);
//        // 配置真实数据源 test2021
//        Map<String, Object> dataSourceProperties2021 = new HashMap<>();
//        dataSourceProperties.put("DriverClassName", driver2021);
//        dataSourceProperties.put("jdbcUrl", url2021);
//        dataSourceProperties.put("username", username2021);
//        dataSourceProperties.put("password", password2021);


        try {
            //com.zaxxer.hikari.HikariDataSource # 数据库连接池类名称
            DataSource ds = DataSourceUtil.getDataSource("com.zaxxer.hikari.HikariDataSource", dataSourceProperties);
            result.put("testmaster", ds);//数据库名
//            DataSource ds2019 = DataSourceUtil.getDataSource("com.zaxxer.hikari.HikariDataSource", dataSourceProperties2019);
//            result.put("test2019", ds2019);//数据库名
//            DataSource ds2020 = DataSourceUtil.getDataSource("com.zaxxer.hikari.HikariDataSource", dataSourceProperties2020);
//            result.put("test2020", ds2020);//数据库名
//            DataSource ds2021 = DataSourceUtil.getDataSource("com.zaxxer.hikari.HikariDataSource", dataSourceProperties2021);
//            result.put("test2021", ds2021);//数据库名

        } catch (ReflectiveOperationException e) {
            e.printStackTrace();
        }
        return result;
    }
}
