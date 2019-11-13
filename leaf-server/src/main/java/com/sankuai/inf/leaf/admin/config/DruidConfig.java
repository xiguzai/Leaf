package com.sankuai.inf.leaf.admin.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import javax.sql.DataSource;

@Configuration
@PropertySource("classpath:leaf.properties")
public class DruidConfig {

    @Value("${leaf.jdbc.url}")
    private String url;
    @Value("${leaf.jdbc.username}")
    private String username;
    @Value("${leaf.jdbc.password}")
    private String password;

    @Bean
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setUrl(url);
        dataSource.setUsername(username);
        dataSource.setPassword(password);
        dataSource.setAsyncInit(true);
        return dataSource;
    }
}
