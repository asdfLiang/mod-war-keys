package com.example.commons.utils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @since 2023/3/18 16:16
 * @author by liangzj
 */
public class FileUtil {

    public static boolean isFile(String path) {
        // TODO
        return true;
    }

    public static boolean isText(String path) {
        // TODO
        return true;
    }

    public static List<String> readFile(String dir, String fileName) {

        Path path = Paths.get(dir, fileName);

        try {
            if (!path.toFile().exists()) {
                throw new FileNotFoundException(String.format("file %s not found", fileName));
            }

            return Files.lines(path).collect(Collectors.toList());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
