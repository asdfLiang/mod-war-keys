package com.example.service.impl;

import static com.example.commons.utils.ThreadUtil.newDaemonThread;

import com.example.commons.utils.FileUtil;
import com.example.dal.entity.CmdHotKeyDO;
import com.example.dal.entity.RefHotKey;
import com.example.service.HotKeyService;
import com.example.service.manager.CmdHotKeyManager;
import com.example.service.manager.LoadRecordManager;
import com.example.service.manager.TranslationManager;
import com.example.service.manager.dto.CmdHotKeyDTO;
import com.example.service.support.enums.CmdTypeEnum;
import com.example.service.support.enums.RaceEnum;
import com.example.service.support.exceptions.HotKeyConflictException;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang3.StringUtils;
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

    @Value("${translation.switch}")
    private boolean translationSwitch;

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
        refreshDB(configFilePath, refHotKeys);

        List<CmdHotKeyDTO> hotKeys =
                refHotKeys.stream().map(this::buildDTO).collect(Collectors.toList());

        // 后台翻译(如果翻译的文本不完整的话)
        if (translationSwitch) newDaemonThread(() -> translationManager.auto(hotKeys)).start();

        return hotKeys;
    }

    @Override
    public List<CmdHotKeyDTO> load(String configFilePath, Integer race) {
        List<CmdHotKeyDTO> all = load(configFilePath);
        if (CollectionUtils.isEmpty(all)) {
            return Collections.emptyList();
        }

        RaceEnum retainRace = RaceEnum.from(race);
        return all.stream()
                .filter(dto -> CmdTypeEnum.isRace(dto.getCmdType().getType(), retainRace))
                .collect(Collectors.toList());
    }

    @Override
    public void update(String cmd, String hotKey, boolean force) {
        if (!StringUtils.isNoneEmpty(cmd, hotKey)) {
            return;
        }

        String pathname = loadRecordManager.latestPathname();
        if (StringUtils.isBlank(pathname)) {
            throw new RuntimeException("未找到要修改的配置文件路径");
        }
        CmdHotKeyDO target = cmdHotKeyManager.requireByCmd(cmd);

        // TODO 格式校验

        // 热键冲突检测
        if (!force) checkConflict(pathname, target, hotKey);

        // 修改快捷键
        cmdHotKeyManager.updateHotKey(pathname, target, hotKey);

        log.info("update cmd {} hotkey to {}", cmd, hotKey);
    }

    private void checkConflict(String pathname, CmdHotKeyDO target, String hotKey)
            throws HotKeyConflictException {
        // 获取所有快捷键
        List<RefHotKey> refHotKeys = cmdHotKeyManager.readHotKeys(pathname);
        if (CollectionUtils.isEmpty(refHotKeys)) {
            return;
        }
        List<CmdHotKeyDTO> cmdHotKeys = refHotKeys.stream().map(this::buildDTO).toList();

        // 过滤冲突快捷键
        List<CmdHotKeyDTO> conflicts =
                cmdHotKeys.stream()
                        .filter(dto -> mayConflict(target, dto))
                        .filter(dto -> Objects.equals(dto.getHotKey(), hotKey))
                        .toList();
        if (CollectionUtils.isEmpty(conflicts)) {
            return;
        }

        throw new HotKeyConflictException("与\n" + joinConflictCmd(conflicts) + "\n冲突");
    }

    private String joinConflictCmd(List<CmdHotKeyDTO> conflicts) {
        Map<Integer, List<CmdHotKeyDTO>> conflictMap =
                conflicts.stream()
                        .collect(Collectors.groupingBy(dto -> dto.getCmdType().getType()));

        // 同类类型指令提示信息拼接, 指令类型[指令1、指令2...指令n]
        Function<? super Map.Entry<Integer, List<CmdHotKeyDTO>>, ? extends String> prettyCmd =
                entry ->
                        CmdTypeEnum.from(entry.getKey()).getRace().getDesc()
                                + "["
                                + entry.getValue().stream()
                                        .map(CmdHotKeyDTO::getTranslation)
                                        .collect(Collectors.joining("、"))
                                + "]";

        // 指令类型1[指令1、指令2...指令n],
        // 指令类型2[指令1、指令2...指令n],
        // ......
        // 指令类型n[指令1、指令2...指令n]
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

        // 同类型快捷键会冲突
        return Objects.equals(target.getCmdType(), may.getCmdType().getType());
    }

    private void refreshDB(String configFilePath, List<RefHotKey> refHotKeys) {
        // 刷新快捷键记录
        Integer hotKeyLines = cmdHotKeyManager.refresh(refHotKeys);
        // 刷新加载记录
        Integer recordLines = loadRecordManager.refresh(configFilePath);

        log.info("insert hotkey {} lines, update load record {} lines", hotKeyLines, recordLines);
    }

    private CmdHotKeyDTO buildDTO(RefHotKey refHotKey) {
        Properties translations = translationManager.getTranslations();

        return CmdHotKeyDTO.builder()
                .cmd(refHotKey.cmd())
                .comments(refHotKey.comments())
                .cmdType(CmdTypeEnum.from(refHotKey.cmdType()))
                .translation(
                        translationSwitch
                                ? translations.getProperty(refHotKey.cmd())
                                : refHotKey.comments())
                .hotKey(refHotKey.hotKey())
                .build();
    }
}
