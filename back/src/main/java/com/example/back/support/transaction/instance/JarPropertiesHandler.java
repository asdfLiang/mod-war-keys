package com.example.back.support.transaction.instance;

import com.example.back.support.enums.EnvEnum;
import com.example.back.support.transaction.PropertiesHandler;
import com.example.commons.exceptions.BaseException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Properties;

/**
 * jar包中的properties文件处理器
 *
 * @since 2023/5/23 22:36
 * @author by liangzj
 */
@Slf4j
@Component
public class JarPropertiesHandler implements PropertiesHandler {
    @Override
    public EnvEnum env() {
        return EnvEnum.prod;
    }

    @Override
    public Properties load(String pathname) {
        Properties properties = PropertiesHandler.super.load(pathname);
        //  把本地的翻译合并进来
        try (FileReader reader =
                new FileReader(obtainLocalPathname(pathname), StandardCharsets.UTF_8)) {
            if (reader.read() == 0) {
                return properties;
            }

            Properties localProperties = new Properties();
            localProperties.load(reader);
            properties.putAll(localProperties);
        } catch (IOException e) {
            log.error("本地翻译文件未找到，pathname： {}", pathname, e);
            throw new BaseException("本地翻译文件未找到");
        }

        return properties;
    }

    @Override
    public void store(String pathname, Properties properties, String comments) {
        PropertiesHandler.super.store(obtainLocalPathname(pathname), properties, comments);
    }

    private String obtainLocalPathname(String pathname) {
        File file = new File(pathname);
        if (file.exists()) {
            return file.getAbsolutePath();
        }

        try {
            if (file.getParentFile().mkdir() && file.createNewFile()) {
                return file.getAbsolutePath();
            }
            throw new BaseException("本地文件创建失败");
        } catch (IOException e) {
            log.error("本地文件创建失败，pathname：{}", pathname, e);
            throw new BaseException("properties写入异常");
        }
    }
}
