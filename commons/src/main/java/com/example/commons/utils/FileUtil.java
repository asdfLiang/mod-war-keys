package com.example.commons.utils;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @since 2023/3/18 16:16
 * @author by liangzj
 */
public class FileUtil {

    public static Path getPath(String dir, String fileName) {
        return Paths.get(dir, fileName);
    }

    public static boolean isFile(String path) {
        // TODO
        return true;
    }

    public static boolean isText(String path) {
        // TODO
        return true;
    }

    public static List<String> readFile(String dir, String fileName) {
        if (StringUtil.isBlank(dir) || StringUtil.isNotBlank(fileName)) {
            return Collections.emptyList();
        }

        try (Stream<String> lines = Files.lines(Paths.get(dir, fileName))) {
            return lines.collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void overwrite() {}
}
