package com.example.back.manager;

import static com.example.back.support.constants.MarkConstant.HOTKEY_START;

import com.example.back.support.HotKeyParser;
import com.example.back.support.entity.RefHotKey;
import com.example.commons.utils.FileUtil;
import com.example.dal.entity.CmdHotKeyDO;
import com.example.dal.mapper.CmdHotKeyMapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @since 2023/5/6 23:24
 * @author by liangzj
 */
@Component
public class CmdHotKeyManager {
    @Autowired private CmdHotKeyMapper cmdHotKeyMapper;

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

        clear();

        List<CmdHotKeyDO> list =
                refHotKeys.stream().map(this::buildDO).collect(Collectors.toList());
        return cmdHotKeyMapper.insertBatch(list);
    }

    /**
     * 获取一条快捷键记录
     *
     * @param cmd 指令
     * @return 快捷键记录
     */
    public CmdHotKeyDO selectByCmd(String cmd) {
        return cmdHotKeyMapper.selectByCmd(cmd);
    }

    /** 清理快捷键记录 */
    public void clear() {
        cmdHotKeyMapper.deleteAll();
        cmdHotKeyMapper.resetSequence();
    }

    // ******************************** 自定义配置文件操作 ******************************** //

    /**
     * 读取快捷键
     *
     * @param pathname 快捷键路径
     * @return 快捷键列表
     */
    public List<RefHotKey> readHotKeys(String pathname) {
        try (BufferedReader reader =
                new BufferedReader(new InputStreamReader(new FileInputStream(pathname)))) {

            // 进行快捷键读取
            return HotKeyParser.parse(reader);

        } catch (FileNotFoundException e) {
            throw new RuntimeException("自定义快捷键配置未找到，请配置文件路径");
        } catch (IOException e) {
            throw new RuntimeException("本地文件读取失败");
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
        // 修改文件行
        FileUtil.updateLine(
                pathname,
                target.getRow(),
                HOTKEY_START + target.getHotKey(),
                HOTKEY_START + newHotKey);
    }

    private CmdHotKeyDO buildDO(RefHotKey refHotKey) {
        CmdHotKeyDO cmdHotKeyDO = new CmdHotKeyDO();
        cmdHotKeyDO.setRow(refHotKey.getRow());
        cmdHotKeyDO.setCmd(refHotKey.getCmd());
        cmdHotKeyDO.setHotKey(refHotKey.getHotKey());

        return cmdHotKeyDO;
    }
}
