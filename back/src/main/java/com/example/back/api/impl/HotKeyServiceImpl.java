package com.example.back.api.impl;

import com.example.back.api.HotKeyService;
import com.example.back.model.CmdHotKeyVO;
import com.example.back.model.RefHotKey;
import com.example.back.support.CustomKeysHelper;
import com.example.commons.utils.FileUtil;

import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @since 2023/3/24 22:42
 * @author by liangzj
 */
@Service
public class HotKeyServiceImpl implements HotKeyService {

    @Override
    public List<CmdHotKeyVO> load(String configFilePath) {
        if (!FileUtil.isFile(configFilePath) || !FileUtil.isText(configFilePath)) {
            throw new RuntimeException("文件路径异常，请检查！");
        }

        // 读取配置文件
        CustomKeysHelper customKeysReader = new CustomKeysHelper(configFilePath);
        List<RefHotKey> refHotKeys = customKeysReader.readHotKeys();
        if (CollectionUtils.isEmpty(refHotKeys)) {
            throw new RuntimeException("配置文件为空！");
        }

        return refHotKeys.stream()
                .map(key -> new CmdHotKeyVO(key.getCmd(), key.getCmd(), key.getHotKey()))
                .collect(Collectors.toList());
    }
}
