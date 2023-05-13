package com.example.front.config;

import lombok.extern.slf4j.Slf4j;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

@Slf4j
@Configuration
public class SqlSessionConfig {

    @Value("${spring.datasource.url}")
    private String url;

    @Bean
    public SqlSessionFactoryBean createSqlSessionFactory() {
        SqlSessionFactoryBean sqlSessionFactoryBean = null;
        try {
            SQLiteDataSource dataSource = new SQLiteDataSource();
            dataSource.setUrl(url);

            // 实例SessionFactory
            sqlSessionFactoryBean = new SqlSessionFactoryBean();

            // 配置数据源
            sqlSessionFactoryBean.setDataSource(dataSource);
        } catch (Exception e) {
            log.error("create sql session factory error", e);
        }
        return sqlSessionFactoryBean;
    }
}
