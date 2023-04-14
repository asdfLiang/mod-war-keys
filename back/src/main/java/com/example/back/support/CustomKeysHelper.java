package com.example.back.support;

import static com.example.back.common.constants.MarkConstant.*;

import com.example.back.model.RefHotKey;

import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

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

    private String filePath;

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
            return doReadHotKeys(reader);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("自定义快捷键配置未找到，请配置文件路径");
        } catch (IOException e) {
            throw new RuntimeException("本地文件读取失败");
        }
    }

    /**
     * 执行快捷键读取
     *
     * @param reader
     * @return
     * @throws IOException
     */
    private static List<RefHotKey> doReadHotKeys(BufferedReader reader) throws IOException {
        // 当前行号
        Integer row = -1;
        // 当前行数据，指令
        String line, cmd = "", comments = "";
        // 用来存放当前配置的所有快捷键
        List<RefHotKey> refHotKeys = new ArrayList<>();

        while (Objects.nonNull(line = reader.readLine())) {
            line = line.trim();
            row++;
            // 获取指令的热键配置，根据热键
            if (line.startsWith(COMMENTS_START)) {
                comments = getComments(line);
            } else if (line.startsWith(CMD_STARTS)) {
                cmd = getCmd(line);
            } else if (line.startsWith(HOTKEY_START)) {
                refHotKeys.add(new RefHotKey(row, cmd, comments, getHotKey(line)).require());
            }
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

    private String fullPath() {
        return dir + System.lineSeparator() + file;
    }
}
