package com.example.back.support.transaction.instance;

import com.example.back.support.enums.EnvEnum;
import com.example.back.support.transaction.PropertiesTemplate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

/**
 * jar包中的properties文件处理器
 *
 * @since 2023/5/23 22:36
 * @author by liangzj
 */
@Slf4j
@Component
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
