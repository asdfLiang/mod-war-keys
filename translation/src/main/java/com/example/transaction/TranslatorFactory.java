package com.example.transaction;

import com.example.transaction.enums.TranslatorEnum;
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

    /**
     * 获取翻译器
     *
     * @param translatorEnum
     * @return
     */
    public Translator get(TranslatorEnum translatorEnum) {
        return translators.stream()
                .filter(translator -> Objects.equals(translatorEnum, translator.type()))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("未知翻译器"));
    }
}
