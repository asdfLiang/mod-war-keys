package com.example.back.support;

import static com.example.back.common.constants.MarkConstant.*;

import com.example.back.model.RefHotKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

/**
 * @since 2023/4/29 10:51
 * @author by liangzj
 */
public class HotKeyParser {
    /** 执行快捷键读取 */
    public static List<RefHotKey> parse(BufferedReader reader) throws IOException {
        Integer row = 0;
        String line, cmd = "", comments = "";
        List<RefHotKey> refHotKeys = new ArrayList<>();

        while (Objects.nonNull(line = reader.readLine())) {
            line = line.trim();
            // 获取指令的热键配置，根据热键
            if (Pattern.matches(COMMENTS_START_REGEX, line)) {
                comments = getComments(line);
            } else if (line.startsWith(CMD_STARTS)) {
                cmd = getCmd(line);
            } else if (line.startsWith(HOTKEY_START)) {
                refHotKeys.add(new RefHotKey(row, cmd, comments, getHotKey(line)).require());
            }
            row++;
        }
        return refHotKeys;
    }

    private static String getComments(String line) {
        return line.replaceFirst("// ", "");
    }

    private static String getCmd(String line) {
        return line.substring(1, line.length() - 1);
    }

    private static String getHotKey(String line) {
        return line.replaceAll(HOTKEY_START, "").trim();
    }
}
