package com.example.back.service.impl;

import static com.example.commons.utils.ThreadUtil.newDaemonThread;

import com.example.back.manager.CmdHotKeyManager;
import com.example.back.manager.LoadRecordManager;
import com.example.back.manager.TranslationManager;
import com.example.back.manager.dto.CmdHotKeyDTO;
import com.example.back.service.HotKeyService;
import com.example.back.support.entity.RefHotKey;
import com.example.back.support.enums.CmdTypeEnum;
import com.example.back.support.exceptions.HotKeyConflictException;
import com.example.commons.utils.FileUtil;
import com.example.commons.utils.PropertiesUtil;
import com.example.commons.utils.StringUtil;
import com.example.dal.entity.CmdHotKeyDO;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.function.Function;
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
    public void update(String cmd, String hotKey, boolean force) {
        if (!StringUtil.noneBlank(cmd, hotKey)) {
            return;
        }

        String pathname = loadRecordManager.latestPathname();
        if (StringUtil.isBlank(pathname)) {
            throw new RuntimeException("未找到要修改的配置文件路径");
        }

        CmdHotKeyDO target = cmdHotKeyManager.requireByCmd(cmd);

        // 不是强制更新，检查热键冲突检查
        if (!force) checkConflict(pathname, target);

        // 修改快捷键
        cmdHotKeyManager.updateHotKey(pathname, target, hotKey);

        log.info("update cmd {} hotkey to {}", cmd, hotKey);
    }

    private void checkConflict(String pathname, CmdHotKeyDO target) throws HotKeyConflictException {
        // 获取所有快捷键
        List<RefHotKey> refHotKeys = cmdHotKeyManager.readHotKeys(pathname);
        if (CollectionUtils.isEmpty(refHotKeys)) {
            return;
        }
        List<CmdHotKeyDTO> cmdHotKeys = refHotKeys.stream().map(this::buildDTO).toList();

        // 可能冲突的快捷键
        List<CmdHotKeyDTO> mayConflicts =
                cmdHotKeys.stream().filter(hotKey -> mayConflict(target, hotKey)).toList();

        // 过滤冲突快捷键
        List<CmdHotKeyDTO> conflicts =
                mayConflicts.stream()
                        .filter(key -> Objects.equals(key.getHotKey(), target.getHotKey()))
                        .toList();
        if (CollectionUtils.isEmpty(conflicts)) {
            return;
        }

        throw new HotKeyConflictException("与\n" + joinConflictCmd(conflicts) + "\n冲突");
    }

    private String joinConflictCmd(List<CmdHotKeyDTO> conflicts) {
        Map<Integer, List<CmdHotKeyDTO>> conflictMap =
                conflicts.stream().collect(Collectors.groupingBy(CmdHotKeyDTO::getCmdType));

        // 同类类型指令提示信息拼接, 指令类型[指令1、指令2...指令n]
        Function<? super Map.Entry<Integer, List<CmdHotKeyDTO>>, ? extends String> prettyCmd =
                entry ->
                        CmdTypeEnum.from(entry.getKey()).getDesc()
                                + "["
                                + entry.getValue().stream()
                                        .map(CmdHotKeyDTO::getTranslation)
                                        .collect(Collectors.joining("、"))
                                + "]";

        return conflictMap.entrySet().stream().map(prettyCmd).collect(Collectors.joining(",\n"));
    }

    /**
     * 是否可能与当前快捷键冲突，只有公共快捷键和同类型的指令才可能冲突
     *
     * @param target 当前快捷键
     * @param may 可能冲突的快捷键
     * @return 是否可能冲突
     */
    private boolean mayConflict(CmdHotKeyDO target, CmdHotKeyDTO may) {
        // 和自己不会冲突
        if (Objects.equals(target.getCmd(), may.getCmd())) {
            return false;
        }

        // 公共快捷键和其他快捷键会冲突
        if (CmdTypeEnum.Shared.getType().equals(target.getCmdType())
                || CmdTypeEnum.Shared.getType().equals(may.getCmdType())) {
            return true;
        }

        // 同类型快捷键会冲突
        return Objects.equals(target.getCmdType(), may.getCmdType());
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
