package com.example.back.service.impl;

import static com.example.commons.utils.ThreadUtil.newDaemonThread;

import com.example.back.manager.CmdHotKeyManager;
import com.example.back.manager.LoadRecordManager;
import com.example.back.manager.TranslationManager;
import com.example.back.manager.dto.CmdHotKeyDTO;
import com.example.back.service.HotKeyService;
import com.example.back.support.entity.RefHotKey;
import com.example.back.support.enums.CmdTypeEnum;
import com.example.commons.utils.FileUtil;
import com.example.commons.utils.PropertiesUtil;
import com.example.dal.entity.CmdHotKeyDO;
import com.example.dal.entity.LoadRecordDO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

/**
 * @since 2023/3/24 22:42
 * @author by liangzj
 */
@Slf4j
@Service
public class HotKeyServiceImpl implements HotKeyService {

    @Value("${translation.pathname}")
    private String pathname;

    @Autowired private CmdHotKeyManager cmdHotKeyManager;

    @Autowired private LoadRecordManager loadRecordManager;

    @Autowired private TranslationManager translationManager;

    @Override
    public List<CmdHotKeyDTO> load(String configFilePath) {
        if (!FileUtil.isText(configFilePath)) {
            throw new RuntimeException("配置文件格式异常，请检查！");
        }

        // 读取配置文件
        List<RefHotKey> refHotKeys = cmdHotKeyManager.readHotKeys(configFilePath);
        if (CollectionUtils.isEmpty(refHotKeys)) {
            throw new RuntimeException("配置文件为空！");
        }

        // 刷新加载记录
        refreshLoadRecord(configFilePath, refHotKeys);

        List<CmdHotKeyDTO> hotKeys =
                refHotKeys.stream().map(this::buildDTO).collect(Collectors.toList());

        // 后台翻译(如果翻译的文本不完整的话)
        newDaemonThread(() -> translationManager.perfect(hotKeys)).start();

        return hotKeys;
    }

    @Override
    public void update(String cmd, String hotKey) {
        CmdHotKeyDO cmdHotKeyDO = cmdHotKeyManager.selectByCmd(cmd);

        LoadRecordDO recordDO = loadRecordManager.latest();
        if (Objects.isNull(recordDO)) {
            throw new RuntimeException("未找到要修改的配置文件路径");
        }

        // 修改快捷键
        cmdHotKeyManager.updateHotKey(recordDO.getPathname(), cmdHotKeyDO, hotKey);

        log.info("update cmd {} hotkey to {}", cmd, hotKey);
    }

    private void refreshLoadRecord(String configFilePath, List<RefHotKey> refHotKeys) {
        // 刷新快捷键记录
        Integer hotKeyLines = cmdHotKeyManager.refresh(refHotKeys);
        // 刷新加载记录
        Integer recordLines = loadRecordManager.refresh(configFilePath);

        log.info("insert hotkey {} lines, update record {} lines", hotKeyLines, recordLines);
    }

    private CmdHotKeyDTO buildDTO(RefHotKey refHotKey) {
        Properties translations = PropertiesUtil.load(FileUtil.getPath(pathname));

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
