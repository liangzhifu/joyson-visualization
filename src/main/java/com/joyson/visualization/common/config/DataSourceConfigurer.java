package com.joyson.visualization.common.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

/**
 * Created by L on 2018/7/16.
 */
@Configuration
@PropertySource(value = "classpath:jdbc.properties")
public class DataSourceConfigurer {

    @Value(value = "${jdbc.driver}")
    private String driver;

    @Value(value = "${jdbc.url}")
    private String url;

    @Value(value = "${jdbc.username}")
    private String username;

    @Value(value = "${jdbc.password}")
    private String password;

    @Bean
    public DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driver);
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setMaxActive(10);
        dataSource.setMinIdle(2);
        return dataSource;
    }
}
