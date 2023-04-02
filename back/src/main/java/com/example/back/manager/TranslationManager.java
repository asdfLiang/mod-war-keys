package com.example.back.manager;

import org.springframework.stereotype.Component;

/**
 * 翻译通用方法
 *
 * @since 2023/3/26 23:03
 * @author by liangzj
 */
@Component
public class TranslationManager {

//    @Value("{translation.engine}")
//    private String translationEngine;
//
//    private final TranslatorFactory translatorFactory;
//
//    public TranslationManager(TranslatorFactory translatorFactory) {
//        this.translatorFactory = translatorFactory;
//    }
//
//    /**
//     * 翻译文本
//     *
//     * @param text 翻译文本
//     * @param from 文本语言
//     * @param to 翻译语言
//     * @return
//     */
//    public String translate(String text, String from, String to) {
//        return translatorFactory
//                .get(TranslatorEnum.from(translationEngine))
//                .translate(text, from, to);
//    }
}
