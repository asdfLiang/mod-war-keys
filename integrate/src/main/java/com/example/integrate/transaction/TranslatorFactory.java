package com.example.integrate.transaction;

import com.example.integrate.transaction.enums.TranslationEngineEnum;

import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;

/**
 * 翻译器工厂
 *
 * @since 2023/3/25 3:55
 * @author by liangzj
 */
@Component
public class TranslatorFactory {
    private final List<Translator> translators;

    public TranslatorFactory(List<Translator> translators) {
        this.translators = translators;
    }

    /** 获取翻译器 */
    public Translator get(TranslationEngineEnum translatorEnum) {
        return translators.stream()
                .filter(translator -> Objects.equals(translatorEnum, translator.engine()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("未知翻译器"));
    }
}
