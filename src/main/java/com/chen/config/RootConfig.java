package com.chen.config;

import org.apache.commons.dbcp.BasicDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import javax.sql.DataSource;

/**
 * Created by 陈忠意 on 2017/7/17.
 */
@Configuration
@ComponentScan(basePackages = { "com.chen"},
    excludeFilters = {
            @ComponentScan.Filter(type= FilterType.ANNOTATION, value= EnableWebMvc.class)
    })
public class RootConfig {

    @Bean
    public BasicDataSource dataSource(){
        BasicDataSource ds = new BasicDataSource();
        ds.setDriverClassName("com.mysql.jdbc.Driver");
        ds.setUrl("jdbc:mysql://localhost:3306/spring?useUnicode=true&amp;characterEncoding=utf-8&useSSL=false");
        ds.setUsername("root");
        ds.setPassword("chen123456");
        ds.setInitialSize(5);
        ds.setMaxActive(10);
        return ds;
    }

    @Bean
    public JdbcTemplate jdbcTemplate(DataSource dataSource){
        return new JdbcTemplate(dataSource);
    }
}
