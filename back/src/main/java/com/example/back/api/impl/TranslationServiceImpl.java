package com.example.back.api.impl;

import com.example.back.api.TranslationService;
import com.example.back.model.CmdHotKeyVO;
import com.example.back.support.TranslationHelper;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

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

    @Override
    public void tryInitTranslationText(List<CmdHotKeyVO> hotKeys) {
        Path path = TranslationHelper.getPath(dir, fileName);
        // 读取翻译文本
        List<String> cmdLines = TranslationHelper.readCmdLines(path);
        // 为空直接写入
        if (CollectionUtils.isEmpty(cmdLines)) {
            TranslationHelper.writeSourceText(
                    path,
                    hotKeys.stream()
                            .map(CmdHotKeyVO::getCmdName)
                            .map(x -> x + "=")
                            .collect(Collectors.toList()));
        }

        // 如果翻译文本和读取的行数一致，说明文本是完整的，无须初始化

        // 如果为空，或者内容不完整，重新创建

    }

    @Override
    public void perfectTranslation() {}
}
