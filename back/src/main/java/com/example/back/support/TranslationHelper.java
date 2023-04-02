package com.example.back.support;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 翻译文件操作工具
 *
 * @since 2023/3/27 21:46
 * @author by liangzj
 */
public class TranslationHelper {

    public static Path getPath(String dir, String fileName) {
        return Paths.get(dir, fileName);
    }

    public static List<String> readCmdLines(Path path) {
        try {
            return Files.lines(path).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 读取翻译文件
     *
     * @param
     * @return
     */
    public Map<String, String> readSourceText() {

        return Collections.emptyMap();
    }

    public static void writeSourceText(Path path, List<String> sourceText) {
        try {
            Files.write(path, sourceText);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
