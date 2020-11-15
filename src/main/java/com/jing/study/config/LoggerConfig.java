package com.jing.study.config;

import com.ctrip.framework.apollo.Config;
import com.ctrip.framework.apollo.model.ConfigChange;
import com.ctrip.framework.apollo.model.ConfigChangeEvent;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfig;
import com.ctrip.framework.apollo.spring.annotation.ApolloConfigChangeListener;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.logging.LogLevel;
import org.springframework.boot.logging.LoggingSystem;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import java.util.Set;

/**
 * @author zhangning
 * @date 2020/10/31
 * 阿波罗的监听
 */
@Configuration
public class LoggerConfig {

    private static final Logger logger = LoggerFactory.getLogger(LoggerConfig.class);
    private static final String LOGGER_TAG = "logging.level.";

    @Autowired
    private LoggingSystem loggingSystem;

    @ApolloConfig
    private Config config;

    @ApolloConfigChangeListener
    //ConfigChangeEvent参数：可以获取被修改配置项的key集合，以及被修改配置项的新值、旧值和修改类型等信息。
    private void configChangeListter(ConfigChangeEvent changeEvent) {
        Set<String> strings = changeEvent.changedKeys();
        for (String key : strings) {
            System.out.println("被修改的key："+key);
            String namespace = changeEvent.getNamespace();
            System.out.println("namespace:"+namespace);
            ConfigChange change = changeEvent.getChange(key);
            System.out.println("老值："+change.getOldValue());
            System.out.println("新值："+ change.getNewValue());
        }
        System.out.println("=====================================");
        refreshLoggingLevels();
    }

    @PostConstruct
    private void refreshLoggingLevels() {
        Set<String> keyNames = config.getPropertyNames();
        for (String key : keyNames) {
//            System.out.println("---apollo中所有的key:"+key);
            if (StringUtils.containsIgnoreCase(key, LOGGER_TAG)) {
                String strLevel = config.getProperty(key, "info");
                LogLevel level = LogLevel.valueOf(strLevel.toUpperCase());
                //通过loggingSystem.setLogLevel修改日志等级
                String loggerName = key.replace(LOGGER_TAG, "");
                System.out.println("++++++loggerName:"+loggerName);
                System.out.println("++++++level:"+level);
                //此行修改系统的日志等级
                loggingSystem.setLogLevel(loggerName, level);
                logger.info("修改了日志级别->>>{}:{}", key, strLevel);
            }
        }
    }


}