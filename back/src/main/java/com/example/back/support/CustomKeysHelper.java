package com.example.back.support;

import static com.example.back.data.constants.MarkConstant.*;

import com.example.back.support.entity.RefHotKey;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.List;
import java.util.regex.Pattern;

/**
 * 自定义配置文件操作工具
 *
 * @since 2023/3/19 17:44
 * @author by liangzj
 */
@Slf4j
public class CustomKeysHelper {

    private String dir;

    private String file;

    private final String filePath;

    public CustomKeysHelper(String filePath) {
        this.filePath = filePath;
    }

    public CustomKeysHelper(String dir, String file) {
        this.dir = dir;
        this.file = file;
        this.filePath = fullPath();
    }

    public List<RefHotKey> readHotKeys() {
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(filePath)))) {

            // 进行快捷键读取
            return HotKeyParser.parse(reader);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("自定义快捷键配置未找到，请配置文件路径");
        } catch (IOException e) {
            throw new RuntimeException("本地文件读取失败");
        }
    }

    private String fullPath() {
        return dir + System.lineSeparator() + file;
    }

    public static void main(String[] args) {
        System.out.println(Pattern.matches(COMMENTS_START_REGEX, "////////////"));
        System.out.println(Pattern.matches(COMMENTS_START_REGEX, "//abc"));
        System.out.println(Pattern.matches(COMMENTS_START_REGEX, "// abc"));
        System.out.println(Pattern.matches(COMMENTS_START_REGEX, "// abc&"));
        System.out.println(Pattern.matches(COMMENTS_START_REGEX, "//orc melee weapon upgrades"));
    }
}
