package com.example.back.manager;

import com.example.back.manager.dto.CmdHotKeyDTO;
import com.example.commons.utils.FileUtil;
import com.example.commons.utils.PropertiesUtil;
import com.example.transaction.TranslatorFactory;
import com.example.transaction.enums.LanguageEnum;
import com.example.transaction.enums.TranslatorEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.nio.file.Path;
import java.util.*;

/**
 * @since 2023/3/25 12:41
 * @author by liangzj
 */
@Component
public class TranslationManager {
    @Value("{translation.engine}")
    private String engine;

    @Value("${translation.pathname}")
    private String pathname;

    @Autowired private TranslatorFactory translatorFactory;

    /** 自动机翻，检查是否有未翻译的文本，如果有，进行翻译 */
    public void machine(List<CmdHotKeyDTO> hotKeys) {
        if (CollectionUtils.isEmpty(hotKeys)) {
            return;
        }

        Path path = FileUtil.getPath(pathname);
        // 读取翻译文本
        Properties local = PropertiesUtil.load(path);

        // 翻译
        hotKeys.forEach(
                vo -> {
                    if (!local.containsKey(vo.getCmd())) {
                        local.put(vo.getCmd(), translate(vo.getComments()));
                        PropertiesUtil.store(path, local, "translation by " + engine);
                    }
                });
    }

    /**
     * 手动翻译
     *
     * @param cmd 指令
     * @param translation 翻译
     */
    public void manual(String cmd, String translation) {
        Path path = FileUtil.getPath(pathname);
        // 读取翻译文本
        Properties local = PropertiesUtil.load(path);

        local.setProperty(cmd, translation);
        PropertiesUtil.store(path, local, "manual update at " + new Date());
    }

    private String translate(String key) {
        return translatorFactory
                .get(TranslatorEnum.from(engine))
                .translate(key, LanguageEnum.EN.name(), LanguageEnum.ZH.name());
    }
}
