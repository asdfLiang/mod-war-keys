package com.example.service.manager;

import com.example.common.utils.FileUtil;
import com.example.dal.entity.CmdHotKeyDO;
import com.example.dal.entity.RefHotKey;
import com.example.dal.mapper.CmdHotKeyMapper;
import com.example.dal.mapper.SequenceMapper;
import com.example.service.support.HotKeyParser;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

/**
 * @since 2023/5/6 23:24
 * @author by liangzj
 */
@Component
public class CmdHotKeyManager {
    @Autowired private CmdHotKeyMapper cmdHotKeyMapper;
    @Autowired private SequenceMapper sequenceMapper;

    // ******************************** 数据库操作 ******************************** //

    /**
     * 刷新快捷键记录
     *
     * @param refHotKeys 快捷键列表
     * @return 刷新行数
     */
    public Integer refresh(List<RefHotKey> refHotKeys) {
        if (CollectionUtils.isEmpty(refHotKeys)) {
            return 0;
        }

        // 清空当前记录
        clear();

        // 重新插入
        List<CmdHotKeyDO> list = refHotKeys.stream().map(this::buildDO).toList();
        return cmdHotKeyMapper.insertBatch(list);
    }

    /**
     * 获取一条快捷键记录
     *
     * @param cmd 指令
     * @return 快捷键记录
     */
    public CmdHotKeyDO requireByCmd(String cmd) {
        if (StringUtils.isBlank(cmd)) {
            return null;
        }

        return Optional.ofNullable(cmdHotKeyMapper.selectByCmd(cmd))
                .orElseThrow(() -> new RuntimeException("要修改的热键信息不存在"));
    }

    /** 清理快捷键记录 */
    public void clear() {
        cmdHotKeyMapper.deleteAll();
        sequenceMapper.resetSequence(SequenceMapper.CMD_HOT_KEY);
    }

    // ******************************** CustomKeys.txt 文件操作 ******************************** //

    /**
     * 读取快捷键
     *
     * @param pathname 快捷键路径
     * @return 快捷键列表
     */
    public List<RefHotKey> readHotKeys(String pathname) {
        if (StringUtils.isBlank(pathname)) {
            return Collections.emptyList();
        }

        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(pathname)))) {

            // 进行快捷键读取
            return HotKeyParser.parse(reader);

        } catch (IOException e) {
            throw new RuntimeException("自定义快捷键配置未找到，请检查配置文件路径");
        }
    }

    /**
     * 更新快捷键
     *
     * @param pathname 配置文件路径
     * @param target 被修改的快捷键信息
     * @param newHotKey 新快捷键
     */
    public void updateHotKey(String pathname, CmdHotKeyDO target, String newHotKey) {
        if (Objects.isNull(target)) {
            return;
        }

        String prefix = HotKeyParser.getHotKeyPrefix(target.getCmd());

        // 修改文件行
        try {
            FileUtil.updateLine(
                    pathname, target.getRow(), prefix + target.getHotKey(), prefix + newHotKey);
        } catch (IOException e) {
            throw new RuntimeException("文件读取失败，请尝试重新加载");
        }

        // 刷新数据库
        refresh(readHotKeys(pathname));
    }

    // ******************************** 其他 ******************************** //

    private CmdHotKeyDO buildDO(RefHotKey refHotKey) {
        CmdHotKeyDO cmdHotKeyDO = new CmdHotKeyDO();
        BeanUtils.copyProperties(refHotKey, cmdHotKeyDO);

        return cmdHotKeyDO;
    }
}
