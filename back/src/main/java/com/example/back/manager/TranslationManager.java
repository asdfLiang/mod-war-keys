package com.example.back.manager;

import com.example.back.manager.dto.CmdHotKeyDTO;
import com.example.back.support.transaction.PropertiesFactory;
import com.example.back.support.transaction.PropertiesTemplate;
import com.example.transaction.TranslatorFactory;
import com.example.transaction.enums.LanguageEnum;
import com.example.transaction.enums.TranslatorEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @since 2023/3/25 12:41
 * @author by liangzj
 */
@Component
public class TranslationManager {
    @Value("${env}")
    private String env;

    @Value("${translation.engine}")
    private String engine;

    @Value("${translation.pathname}")
    private String pathname;

    @Autowired private TranslatorFactory translatorFactory;

    @Autowired private PropertiesFactory propertiesFactory;

    /** 自动机翻 */
    public void auto(List<CmdHotKeyDTO> hotKeys) {
        if (CollectionUtils.isEmpty(hotKeys)) {
            return;
        }

        PropertiesTemplate template = propertiesFactory.getTemplate(env);

        // 读取翻译文本
        Properties local = template.load(pathname);

        // 翻译
        hotKeys.forEach(
                vo -> {
                    if (!local.containsKey(vo.getCmd())) {
                        local.put(vo.getCmd(), translate(vo.getComments()));
                        template.store(pathname, local, "translation by " + engine);
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
        PropertiesTemplate template = propertiesFactory.getTemplate(env);

        // 读取翻译文本
        Properties local = template.load(pathname);

        local.setProperty(cmd, translation);
        template.store(pathname, local, "manual update");
    }

    /**
     * 获取本地翻译文件
     *
     * @return 本地翻译文件
     */
    public Properties getTranslations() {
        return propertiesFactory.getTemplate(env).load(pathname);
    }

    private String translate(String key) {
        return translatorFactory
                .get(TranslatorEnum.from(engine))
                .translate(key, LanguageEnum.EN.name(), LanguageEnum.ZH.name());
    }
}
