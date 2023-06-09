package com.example.common.utils;

import com.example.common.exceptions.FileExpireException;
import com.example.common.exceptions.FileLineOutOfBoundsException;
import com.example.common.exceptions.PathNotFoundException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

/**
 * @since 2023/3/18 16:16
 * @author by liangzj
 */
@Slf4j
public class FileUtil {

    public static Path getPath(String pathname) {
        if (StringUtils.isBlank(pathname)) {
            throw new IllegalArgumentException("pathname不能为空");
        }

        try {
            File file = new File(pathname);

            return Paths.get(file.getParent(), file.getName());
        } catch (Exception e) {
            log.warn("路径未找到, pathname: {}", pathname, e);
            throw new PathNotFoundException("文件路径未找到!");
        }
    }

    public static boolean isText(String pathname) {
        if (StringUtils.isBlank(pathname)) {
            return false;
        }

        File file = new File(pathname);
        return file.isFile() && file.getName().endsWith(".txt");
    }

    public static void updateLine(String pathname, Integer lineNumber, String expect, String update)
            throws IOException {
        Path path = getPath(pathname);
        List<String> lines = Files.readAllLines(path);
        // 检查行数
        if (lineNumber < 0 || lineNumber > lines.size()) {
            throw new FileLineOutOfBoundsException("要修改的行不存在：" + lineNumber);
        }

        // 检查当前内容是否与导入时一致
        if (!Objects.equals(expect, lines.get(lineNumber))) {
            log.error("file expire, expect: {}, value: {}", expect, lines.get(lineNumber));
            throw new FileExpireException("文件已被修改，请重新加载");
        }

        // 覆写指定行内容
        lines.set(lineNumber, update);
        Files.write(path, lines);
    }
}
