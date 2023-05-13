package com.example.back.service;

import com.example.back.manager.dto.CmdHotKeyDTO;
import com.example.back.support.enums.RaceEnum;

import java.util.List;

/**
 * @since 2023/3/24 22:40
 * @author by liangzj
 */
public interface HotKeyService {

    /**
     * 读取用户本地自定义快捷键文件
     *
     * @param configFilePath 自定义热键配置文件路径
     * @return 读取到的热键信息
     */
    List<CmdHotKeyDTO> load(String configFilePath);

    /**
     * 根据种族读取用户本地自定义快捷键文件
     *
     * @param configFilePath 自定义热键配置文件路径
     * @param race {@link RaceEnum#getRace() 种族}
     * @return 读取到的热键信息
     */
    List<CmdHotKeyDTO> load(String configFilePath, Integer race);

    /**
     * 更新热键
     *
     * @param cmd 指令
     * @param hotKey 热键
     * @param force 强制更新，忽略热键冲突
     */
    void update(String cmd, String hotKey, boolean force);
}
