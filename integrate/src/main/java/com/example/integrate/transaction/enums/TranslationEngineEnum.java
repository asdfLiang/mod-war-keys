package com.example.integrate.transaction.enums;

import java.util.Arrays;

/**
 * 翻译引擎枚举
 *
 * @since 2023/3/25 3:54
 * @author by liangzj
 */
public enum TranslationEngineEnum {
    Baidu,
    DeepL,
    Microsoft,
    ;

    /** 默认DeepL */
    public static TranslationEngineEnum from(String name) {
        return Arrays.stream(TranslationEngineEnum.values())
                .filter(t -> t.name().equals(name))
                .findFirst()
                .orElse(DeepL);
    }
}
