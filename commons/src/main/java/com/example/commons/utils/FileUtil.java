package com.example.commons.utils;

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
public class FileUtil {

    public static Path getPath(String pathname) {
        if (StringUtil.isBlank(pathname)) {
            throw new IllegalArgumentException("pathname不能为空");
        }

        File file = new File(pathname);

        return Paths.get(file.getParent(), file.getName());
    }

    public static boolean isText(String pathname) {
        if (StringUtil.isBlank(pathname)) {
            throw new IllegalArgumentException("pathname不能为空");
        }

        File file = new File(pathname);
        return file.isFile() && file.getName().endsWith(".txt");
    }

    public static void updateLine(
            String pathname, Integer lineNumber, String expect, String update) {
        Path path = getPath(pathname);
        try {
            List<String> lines = Files.readAllLines(path);
            // 检查行数
            if (lineNumber < 0 || lineNumber > lines.size()) {
                throw new IllegalArgumentException("要修改的行数不存在");
            }

            // 检查当前内容是否与导入时一致
            if (!Objects.equals(expect, lines.get(lineNumber))) {
                throw new IllegalArgumentException("文件已被修改，请重新加载");
            }

            // 覆写指定行内容
            lines.set(lineNumber, update);
            Files.write(path, lines);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
