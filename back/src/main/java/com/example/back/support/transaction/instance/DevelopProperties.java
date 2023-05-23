package com.example.back.support.transaction.instance;

import com.example.back.support.enums.EnvEnum;
import com.example.back.support.transaction.PropertiesTemplate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.io.File;

/**
 * 开发环境properties处理器
 *
 * @since 2023/5/23 22:36
 * @author by liangzj
 */
@Slf4j
@Component
public class DevelopProperties extends PropertiesTemplate {
    private static final String RESOURCES_PATH = "front/src/main/resources";

    @Override
    public EnvEnum env() {
        return EnvEnum.dev;
    }

    @Override
    public String envPathname(String pathname) {
        String absolutePath = new File(RESOURCES_PATH + "/" + pathname).getAbsolutePath();

        obtainPathname(absolutePath);

        return absolutePath;
    }
}
