package com.example.back.support.transaction.instance;

import com.example.back.support.enums.EnvEnum;
import com.example.back.support.transaction.PropertiesHandler;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.io.File;
import java.util.Properties;

/**
 * 开发环境properties处理器
 *
 * @since 2023/5/23 22:36
 * @author by liangzj
 */
@Slf4j
@Component
public class DevelopPropertiesHandler implements PropertiesHandler {
    private static final String RESOURCES_PATH = "front/src/main/resources";

    @Override
    public EnvEnum env() {
        return EnvEnum.dev;
    }

    @Override
    public Properties load(String pathname) {
        return PropertiesHandler.super.load(pathname);
    }

    @Override
    public void store(String pathname, Properties properties, String comments) {
        String absolutePath = new File(RESOURCES_PATH + "/" + pathname).getAbsolutePath();

        PropertiesHandler.super.store(absolutePath, properties, comments);
    }
}
