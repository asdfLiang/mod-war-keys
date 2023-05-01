package com.example.back.service.impl;

import static com.example.commons.utils.ThreadUtil.newDaemonThread;

import com.example.back.data.enums.CmdTypeEnum;
import com.example.back.model.CmdHotKeyDTO;
import com.example.back.model.RefHotKey;
import com.example.back.service.HotKeyService;
import com.example.back.service.TranslationService;
import com.example.back.support.CustomKeysHelper;
import com.example.commons.utils.FileUtil;
import com.example.commons.utils.PropertiesUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @since 2023/3/24 22:42
 * @author by liangzj
 */
@Service
public class HotKeyServiceImpl implements HotKeyService {

    @Value("${translation.file.dir}")
    private String dir;

    @Value("${translation.file.name}")
    private String fileName;

    @Autowired private TranslationService translationService;

    @Override
    public List<CmdHotKeyDTO> load(String configFilePath) {
        if (!FileUtil.isFile(configFilePath) || !FileUtil.isText(configFilePath)) {
            throw new RuntimeException("文件路径异常，请检查！");
        }

        // 读取配置文件
        CustomKeysHelper customKeysReader = new CustomKeysHelper(configFilePath);
        List<RefHotKey> refHotKeys = customKeysReader.readHotKeys();
        if (CollectionUtils.isEmpty(refHotKeys)) {
            throw new RuntimeException("配置文件为空！");
        }

        List<CmdHotKeyDTO> hotKeys =
                refHotKeys.stream().map(this::buildDTO).collect(Collectors.toList());

        // 后台翻译(如果翻译的文本不完整的话)
        newDaemonThread(() -> translationService.perfectTranslation(hotKeys)).start();

        return hotKeys;
    }

    private CmdHotKeyDTO buildDTO(RefHotKey refHotKey) {
        Properties translations = PropertiesUtil.load(Path.of(dir, fileName));

        return CmdHotKeyDTO.builder()
                .cmd(refHotKey.getCmd())
                .comments(refHotKey.getComments())
                .cmdType(refHotKey.getCmdType())
                .cmdTypeDesc(CmdTypeEnum.from(refHotKey.getCmdType()).getDesc())
                .translation(translations.getProperty(refHotKey.getCmd()))
                .hotKey(refHotKey.getHotKey())
                .build();
    }
}
