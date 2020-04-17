package com.vua.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

import javax.sql.DataSource;

/**
 * @program: miaosha
 * @description:
 * @author: vua
 * @create: 2020-04-09 20:31
 */
@Configuration
public class TxConfig {
    @Autowired
    DataSource dataSource;
    //使用jpa时需要用户自定义transactionManager
    @Bean
    public DataSourceTransactionManager transactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }
}
