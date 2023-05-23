package com.example.back.support.transaction;

import com.example.back.support.enums.EnvEnum;
import com.example.commons.exceptions.BaseException;

import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

/**
 * properties文件处理器
 *
 * @since 2023/5/23 22:34
 * @author by liangzj
 */
public interface PropertiesHandler {

    EnvEnum env();

    default void store(String pathname, Properties properties, String comments) {
        // 新增的翻译写到本地
        try (FileWriter fw = new FileWriter(pathname, StandardCharsets.UTF_8)) {
            properties.store(fw, comments);
        } catch (IOException e) {
            throw new BaseException("properties写入异常", e);
        }
    }

    default Properties load(String pathname) {
        try (InputStream in =
                        PropertiesHandler.class.getClassLoader().getResourceAsStream(pathname);
                InputStreamReader reader =
                        new InputStreamReader(Objects.requireNonNull(in), StandardCharsets.UTF_8)) {

            Properties properties = new Properties();
            properties.load(reader);

            return properties;
        } catch (IOException e) {
            throw new BaseException("properties读取失败", e);
        }
    }
}
