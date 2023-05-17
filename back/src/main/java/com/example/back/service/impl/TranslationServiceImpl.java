package com.example.back.service.impl;

import com.example.back.manager.TranslationManager;
import com.example.back.service.TranslationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @since 2023/5/11 21:02
 * @author by liangzj
 */
@Service
public class TranslationServiceImpl implements TranslationService {
    @Value("${translation.switch}")
    private boolean translationSwitch;

    @Autowired private TranslationManager translationManager;

    @Override
    public void manual(String cmd, String translation) {
        if (!translationSwitch) {
            return;
        }

        translationManager.manual(cmd, translation);
    }
}
