package com.example.transaction;

import com.example.transaction.enums.TranslatorEnum;
/**
 * 翻译器
 *
 * @since 2023/3/25 2:35
 * @author by liangzj
 */
public interface Translator {

    TranslatorEnum type();

    /**
     * 翻译文本
     *
     * @param text 文本
     * @param from 来源语言
     * @param to 目标语言
     * @return
     */
    String translate(String text, String from, String to);
}
