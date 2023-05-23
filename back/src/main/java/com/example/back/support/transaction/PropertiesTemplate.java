package com.example.back.support.transaction;

import com.example.back.support.enums.EnvEnum;
import com.example.commons.exceptions.BaseException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;
import java.util.Properties;

/**
 * properties文件处理模板
 *
 * @since 2023/5/23 22:34
 * @author by liangzj
 */
@Slf4j
@Component
public abstract class PropertiesTemplate {
    /**
     * 加载配置文件
     *
     * @param name 文件路径名称，相对于resources文件夹
     * @return properties文件
     */
    public Properties load(String name) {
        try (InputStream in = PropertiesTemplate.class.getClassLoader().getResourceAsStream(name);
                InputStreamReader reader =
                        new InputStreamReader(Objects.requireNonNull(in), StandardCharsets.UTF_8);
                FileReader envReader = new FileReader(envPathname(name), StandardCharsets.UTF_8)) {
            // 项目编译路径下的配置文件
            Properties properties = new Properties();
            properties.load(reader);

            if (reader.read() == 0) {
                return properties;
            }

            // 合并不同环境用户修改过的配置文件
            Properties envProperties = new Properties();
            envProperties.load(envReader);
            properties.putAll(envProperties);

            return properties;
        } catch (IOException e) {
            throw new BaseException("properties读取失败", e);
        }
    }

    /**
     * 写入properties文件内容
     *
     * @param name 文件路径名称，相对于resources文件夹
     * @param properties 要写入的properties
     * @param comments 写入说明
     */
    public void store(String name, Properties properties, String comments) {
        // 新增的翻译写到本地
        try (FileWriter fw = new FileWriter(envPathname(name), StandardCharsets.UTF_8)) {
            properties.store(fw, comments);
        } catch (IOException e) {
            throw new BaseException("properties写入异常", e);
        }
    }

    /**
     * 获取文件路径，如果文件不存在，先创建再返回路径
     *
     * @param pathname 文件路径
     * @return 文件绝对路径
     */
    protected String obtainPathname(String pathname) {
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
            throw new BaseException("properties写入异常");
        }
    }

    public abstract EnvEnum env();

    /**
     * 不同环境的文件路径
     *
     * @param pathname 文件名称
     * @return 当前环境配置文件绝对路径
     */
    public abstract String envPathname(String pathname);
}
