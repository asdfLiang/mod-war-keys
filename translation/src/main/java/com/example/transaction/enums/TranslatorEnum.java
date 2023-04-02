package com.example.transaction.enums;

import java.util.Arrays;

/**
 * 翻译引擎枚举
 *
 * @since 2023/3/25 3:54
 * @author by liangzj
 */
public enum TranslatorEnum {
    Baidu,
    DeepL;

    /**
     * 默认DeepL
     *
     * @param name
     * @return
     */
    public static TranslatorEnum from(String name) {
        return Arrays.stream(TranslatorEnum.values())
                .filter(t -> t.name().equals(name))
                .findFirst()
                .orElse(DeepL);
    }
}
