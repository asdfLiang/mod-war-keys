package com.example.back.api.impl;

import com.example.back.api.TranslationService;
import com.example.back.model.CmdHotKeyVO;
import com.example.commons.utils.FileUtil;
import com.example.commons.utils.PropertiesUtil;
import com.example.transaction.TranslatorFactory;
import com.example.transaction.enums.LanguageEnum;
import com.example.transaction.enums.TranslatorEnum;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.*;

/**
 * @since 2023/3/25 12:41
 * @author by liangzj
 */
@Service
public class TranslationServiceImpl implements TranslationService {
    @Value("${translation.file.dir}")
    private String dir;

    @Value("${translation.file.name}")
    private String fileName;

    private final TranslatorFactory translatorFactory;

    public TranslationServiceImpl(TranslatorFactory translatorFactory) {
        this.translatorFactory = translatorFactory;
    }

    @Override
    public void perfectTranslation(List<CmdHotKeyVO> hotKeys) {
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
                .get(TranslatorEnum.DeepL)
                .translate(key, LanguageEnum.EN.name(), LanguageEnum.ZH.name());
    }
}
