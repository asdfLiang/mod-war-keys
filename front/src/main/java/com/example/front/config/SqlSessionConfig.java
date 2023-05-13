package com.example.front.config;

import lombok.extern.slf4j.Slf4j;

import org.mybatis.spring.SqlSessionFactoryBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.sqlite.SQLiteDataSource;

@Slf4j
@Configuration
public class SqlSessionConfig {

    @Value("${spring.datasource.url}")
    private String url;

    /**
     * 配置多数据源导致下划线命名映射为驼峰失效，需要在这里加一个配置，并在下面SqlSessionFactory方法中引入
     *
     * @return 配置
     */
    @Bean
    @ConfigurationProperties(prefix = "mybatis.configuration")
    public org.apache.ibatis.session.Configuration globalConfiguration() {
        return new org.apache.ibatis.session.Configuration();
    }

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

            // 配置下划线驼峰自动转换
            sqlSessionFactoryBean.setConfiguration(globalConfiguration());
        } catch (Exception e) {
            log.error("create sql session factory error", e);
        }
        return sqlSessionFactoryBean;
    }
}
