package com.sankuai.inf.leaf.admin.config;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.io.IOException;

@MapperScan("com.sankuai.inf.leaf.admin.dao")
@Configuration
public class MyBatisConfig {

    @Autowired
    private DataSource dataSource;

    @Bean
    public SqlSessionFactoryBean sqlSessionFactoryBean() throws IOException {
        SqlSessionFactoryBean sqlSessionFactoryBean = new SqlSessionFactoryBean();
        sqlSessionFactoryBean.setDataSource(dataSource);
        Resource[] resources = new PathMatchingResourcePatternResolver().getResources("classpath:mapper/**/*.xml");
        sqlSessionFactoryBean.setMapperLocations(resources);
        return sqlSessionFactoryBean;
    }

}
