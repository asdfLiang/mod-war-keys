package com.example.back.api;

import com.example.back.model.CmdHotKeyVO;

import java.util.List;

/**
 * @since 2023/3/24 22:40
 * @author by liangzj
 */
public interface HotKeyService {

    /**
     * 读取用户本地自定义快捷键文件
     *
     * @param configFilePath
     * @return
     */
    List<CmdHotKeyVO> load(String configFilePath);
}
