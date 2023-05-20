package com.example.commons.utils;

import com.example.commons.exceptions.BaseException;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

/**
 * @since 2023/4/5 0:07
 * @author by liangzj
 */
@Slf4j
public class PropertiesUtil {

    public static Properties load(String pathname) {
        try (InputStream in = PropertiesUtil.class.getClassLoader().getResourceAsStream(pathname);
                InputStreamReader reader =
                        new InputStreamReader(Objects.requireNonNull(in), StandardCharsets.UTF_8)) {

            Properties properties = new Properties();
            properties.load(reader);

            return properties;
        } catch (IOException e) {
            log.warn("properties读取失败", e);
            throw new BaseException("properties读取失败");
        }
    }

    public static void store(String pathname, Properties properties, String comments) {

        try (FileWriter fw = new FileWriter(pathname, StandardCharsets.UTF_8)) {
            properties.store(fw, comments);
        } catch (IOException e) {
            log.warn("properties写入异常", e);
            throw new BaseException("properties写入异常");
        }
    }
}
