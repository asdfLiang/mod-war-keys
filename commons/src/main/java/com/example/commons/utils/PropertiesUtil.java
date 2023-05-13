package com.example.commons.utils;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

/**
 * @since 2023/4/5 0:07
 * @author by liangzj
 */
@Slf4j
public class PropertiesUtil {

    public static Properties load(Path path) {

        try {
            Properties properties = new Properties();
            properties.load(new FileReader(path.toFile()));
            return properties;
        } catch (IOException e) {
            log.warn("properties导入失败, root: {}", new File("").getAbsolutePath(), e);
            throw new RuntimeException("文件加载异常!");
        }
    }

    public static void store(Path path, Properties properties, String comments) {

        try (FileWriter fw = new FileWriter(path.toFile())) {
            properties.store(fw, comments);
        } catch (IOException e) {
            throw new RuntimeException("properties写入异常", e);
        }
    }
}
