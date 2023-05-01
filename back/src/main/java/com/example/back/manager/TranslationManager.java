package com.example.back.manager;

import com.example.back.manager.dto.CmdHotKeyDTO;
import com.example.commons.utils.FileUtil;
import com.example.commons.utils.PropertiesUtil;
import com.example.transaction.TranslatorFactory;
import com.example.transaction.enums.LanguageEnum;
import com.example.transaction.enums.TranslatorEnum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.nio.file.Path;
import java.util.*;

/**
 * @since 2023/3/25 12:41
 * @author by liangzj
 */
@Component
public class TranslationManager {
    @Value("{translation.engine}")
    private String translationEngine;

    @Value("${translation.file.dir}")
    private String dir;

    @Value("${translation.file.name}")
    private String fileName;

    private final TranslatorFactory translatorFactory;

    public TranslationManager(TranslatorFactory translatorFactory) {
        this.translatorFactory = translatorFactory;
    }

    /** 完善翻译，检查是否有未翻译的文本，如果有，进行翻译 */
    public void perfect(List<CmdHotKeyDTO> hotKeys) {
        Path path = FileUtil.getPath(dir, fileName);
        // 读取翻译文本
        Properties local = PropertiesUtil.load(path);

        // 翻译
        hotKeys.forEach(
                vo -> {
                    if (!local.containsKey(vo.getCmd())) {
                        local.put(vo.getCmd(), translate(vo.getComments()));
                        PropertiesUtil.store(path, local, "translation...");
                    }
                });
    }

    private String translate(String key) {
        return translatorFactory
                .get(TranslatorEnum.from(translationEngine))
                .translate(key, LanguageEnum.EN.name(), LanguageEnum.ZH.name());
    }
}
