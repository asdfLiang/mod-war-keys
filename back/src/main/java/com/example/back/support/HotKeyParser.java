package com.example.back.support;

import static com.example.back.support.constants.MarkConstant.*;

import com.example.back.support.enums.CmdTypeEnum;
import com.example.dal.entity.RefHotKey;

import org.apache.commons.lang3.StringUtils;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

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
            } else if (isComments(line)) {
                comments = getComments(line);
            } else if (line.startsWith(CMD_STARTS)) {
                cmd = getCmd(line);
            } else if (line.startsWith(HOTKEY_START)) {
                refHotKeys.add(
                        new RefHotKey(row, cmd, cmdType, comments, getHotKey(line)).require());
            } else if (line.startsWith(RESEARCH_HOTKEY_START)) {
                refHotKeys.add(
                        new RefHotKey(row, getResCmd(cmd), cmdType, comments, getResHotKey(line))
                                .require());
            } else if (line.startsWith(UN_HOTKEY_START)) {
                refHotKeys.add(
                        new RefHotKey(row, getUnCmd(cmd), cmdType, comments, getUnHotKey(line))
                                .require());
            }
            row++;
        }
        return refHotKeys;
    }

    public static String getHotKeyPrefix(String cmd) {
        if (cmd.startsWith(UN_CMD_START)) {
            return UN_HOTKEY_START;
        } else if (cmd.startsWith(RES_CMD_START)) {
            return RESEARCH_HOTKEY_START;
        } else {
            return HOTKEY_START;
        }
    }

    private static boolean isComments(String line) {
        if (StringUtils.isBlank(line)) {
            return false;
        }
        // "//"开头，且包含其他字符的就是注释
        return line.startsWith(COMMENTS_START)
                && StringUtils.isNotBlank(line.replaceAll("/", "").trim());
    }

    private static Integer getCmdType(String line) {
        return Optional.ofNullable(CmdTypeEnum.fromTitle(line))
                .map(CmdTypeEnum::getType)
                .orElse(null);
    }

    private static String getComments(String line) {
        return line.replaceFirst(COMMENTS_START, "").trim();
    }

    private static String getCmd(String line) {
        return line.substring(1, line.length() - 1);
    }

    private static String getResCmd(String cmd) {
        return "Res" + cmd;
    }

    private static String getUnCmd(String cmd) {
        return "Un" + cmd;
    }

    private static String getHotKey(String line) {
        return line.replaceAll(HOTKEY_START, "").trim();
    }

    private static String getResHotKey(String line) {
        return line.replaceAll(RESEARCH_HOTKEY_START, "").trim();
    }

    private static String getUnHotKey(String line) {
        return line.replaceAll(UN_HOTKEY_START, "").trim();
    }

    //    public static void main(String[] args) {
    //        System.out.println(Pattern.matches(COMMENTS_START_REGEX, "////////////"));
    //        System.out.println(Pattern.matches(COMMENTS_START_REGEX, "//abc"));
    //        System.out.println(Pattern.matches(COMMENTS_START_REGEX, "// abc"));
    //        System.out.println(Pattern.matches(COMMENTS_START_REGEX, "// abc&"));
    //        System.out.println(Pattern.matches(COMMENTS_START_REGEX, "//orc melee weapon
    // upgrades"));
    //    }
}
