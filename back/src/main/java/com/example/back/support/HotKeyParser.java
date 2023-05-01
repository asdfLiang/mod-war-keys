package com.example.back.support;

import static com.example.back.data.constants.MarkConstant.*;

import com.example.back.data.enums.CmdTypeEnum;
import com.example.back.model.RefHotKey;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.regex.Pattern;

/**
 * @since 2023/4/29 10:51
 * @author by liangzj
 */
public class HotKeyParser {
    /** 执行快捷键读取 */
    public static List<RefHotKey> parse(BufferedReader reader) throws IOException {
        Integer row = 0, cmdType = 0;
        String line, cmd = "", comments = "";
        List<RefHotKey> refHotKeys = new ArrayList<>();

        while (Objects.nonNull(line = reader.readLine())) {
            line = line.trim();
            Integer cmdType0 = getCmdType(line);
            // 获取指令的热键配置
            if (cmdType0 != null) {
                cmdType = cmdType0;
            } else if (Pattern.matches(COMMENTS_START_REGEX, line)) {
                comments = getComments(line);
            } else if (line.startsWith(CMD_STARTS)) {
                cmd = getCmd(line);
            } else if (line.startsWith(HOTKEY_START)) {
                refHotKeys.add(
                        new RefHotKey(row, cmd, cmdType, comments, getHotKey(line)).require());
            }
            row++;
        }
        return refHotKeys;
    }

    private static Integer getCmdType(String line) {
        return Optional.ofNullable(CmdTypeEnum.fromTitle(line))
                .map(CmdTypeEnum::getType)
                .orElse(null);
    }

    private static String getComments(String line) {
        return line.replaceFirst(COMMENTS_START, "");
    }

    private static String getCmd(String line) {
        return line.substring(1, line.length() - 1);
    }

    private static String getHotKey(String line) {
        return line.replaceAll(HOTKEY_START, "").trim();
    }
}
