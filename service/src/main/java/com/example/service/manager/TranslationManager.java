package com.example.service.manager;

import com.example.integrate.transaction.TranslatorFactory;
import com.example.integrate.transaction.enums.LanguageEnum;
import com.example.integrate.transaction.enums.TranslationEngineEnum;
import com.example.service.manager.dto.CmdHotKeyDTO;
import com.example.service.support.templates.PropertiesTemplate;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.*;

/**
 * @since 2023/3/25 12:41
 * @author by liangzj
 */
@Slf4j
@Component
public class TranslationManager {
    @Value("${translation.engine}")
    private String engine;

    @Value("${translation.pathname}")
    private String pathname;

    @Autowired private TranslatorFactory translatorFactory;

    @Autowired private PropertiesTemplate propertiesTemplate;

    /** 自动机翻 */
    public void auto(List<CmdHotKeyDTO> hotKeys) {
        if (CollectionUtils.isEmpty(hotKeys)) {
            return;
        }

        // 读取翻译文本
        Properties local = propertiesTemplate.load(pathname);

        try {
            // 翻译
            hotKeys.forEach(
                    vo -> {
                        if (!local.containsKey(vo.getCmd())) {
                            local.put(vo.getCmd(), translate(vo.getComments()));
                            propertiesTemplate.store(pathname, local, "translation by " + engine);
                        }
                    });
        } catch (Exception e) {
            log.warn("自动翻译失败", e);
        }
    }

    /**
     * 手动翻译
     *
     * @param cmd 指令
     * @param translation 翻译
     */
    public void manual(String cmd, String translation) {

        // 读取翻译文本
        Properties local = propertiesTemplate.load(pathname);

        local.setProperty(cmd, translation);
        propertiesTemplate.store(pathname, local, "manual update");
    }

    /**
     * 获取本地翻译文件
     *
     * @return 本地翻译文件
     */
    public Properties getTranslations() {
        return propertiesTemplate.load(pathname);
    }

    private String translate(String key) {
        return translatorFactory
                .get(TranslationEngineEnum.from(engine))
                .translate(key, LanguageEnum.EN.name(), LanguageEnum.ZH.name());
    }
}
