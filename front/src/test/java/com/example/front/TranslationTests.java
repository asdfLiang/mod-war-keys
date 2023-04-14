package com.example.front;

import com.example.FrontApplication;
import com.example.transaction.Translator;
import com.example.transaction.TranslatorFactory;
import com.example.transaction.enums.LanguageEnum;
import com.example.transaction.enums.TranslatorEnum;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;

/**
 * @since 2023/4/14 23:37
 * @author by liangzj
 */
@ContextConfiguration(classes = {FrontApplication.class})
@SpringBootTest
public class TranslationTests {

    @Autowired private TranslatorFactory translatorFactory;

    @Test
    public void testMicrosoftTranslator() {
        Translator translator = translatorFactory.get(TranslatorEnum.Microsoft);

        System.out.println(translator.translate("こんにちは", null, LanguageEnum.ZH.name()));
        System.out.println(translator.translate("Hello World", null, LanguageEnum.ZH.name()));
    }
}
