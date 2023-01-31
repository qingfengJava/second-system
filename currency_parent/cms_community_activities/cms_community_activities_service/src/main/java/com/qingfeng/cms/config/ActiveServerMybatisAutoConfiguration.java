package com.qingfeng.cms.config;

import com.qingfeng.currency.database.datasource.BaseMybatisConfiguration;
import com.qingfeng.currency.database.properties.DatabaseProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * 配置一些拦截器
 * @author 清风学Java
 */
@Configuration
@Slf4j
public class ActiveServerMybatisAutoConfiguration extends BaseMybatisConfiguration {

    public ActiveServerMybatisAutoConfiguration(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }


}