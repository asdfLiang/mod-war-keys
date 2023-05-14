package com.example.front.controller.action;

import javafx.scene.input.Clipboard;
import javafx.scene.input.ClipboardContent;

/**
 * 系统操作
 *
 * @since 2023/5/14 9:58
 * @author by liangzj
 */
public class SystemAction {

    /** 复制文本到剪贴板 */
    public static void putClipboard(String string) {
        Clipboard clipboard = Clipboard.getSystemClipboard();
        ClipboardContent content = new ClipboardContent();
        content.putString(string);
        clipboard.setContent(content);
    }
}
