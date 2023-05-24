package com.example.service.impl;

import com.example.service.TranslationService;
import com.example.service.manager.TranslationManager;

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
