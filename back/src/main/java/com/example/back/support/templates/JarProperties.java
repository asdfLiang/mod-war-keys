package com.example.back.support.templates;

import com.example.back.support.enums.EnvEnum;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/**
 * jar包中的properties文件处理器
 *
 * @since 2023/5/23 22:36
 * @author by liangzj
 */
@Slf4j
@Component
@ConditionalOnProperty(name = {"env"}, havingValue = "prod")
public class JarProperties extends PropertiesTemplate {
    @Override
    public EnvEnum env() {
        return EnvEnum.prod;
    }

    @Override
    public String envPathname(String pathname) {
        return obtainPathname(pathname);
    }
}
