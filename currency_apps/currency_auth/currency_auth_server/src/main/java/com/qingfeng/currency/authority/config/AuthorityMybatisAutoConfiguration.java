package com.qingfeng.currency.authority.config;

import com.qingfeng.currency.database.datasource.BaseMybatisConfiguration;
import com.qingfeng.currency.database.properties.DatabaseProperties;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;

/**
 * 和mybatis相关的配置类
 *
 * @author 清风学Java
 * @version 1.0.0
 * @date 2022/9/16
 */
@Configuration
@Slf4j
public class AuthorityMybatisAutoConfiguration extends BaseMybatisConfiguration {

    public AuthorityMybatisAutoConfiguration(DatabaseProperties databaseProperties) {
        super(databaseProperties);
    }
}
