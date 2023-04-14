package com.example.back.api.impl;

import com.example.back.api.HotKeyService;
import com.example.back.model.CmdHotKeyVO;
import com.example.back.model.RefHotKey;
import com.example.back.support.CustomKeysHelper;
import com.example.commons.utils.FileUtil;
import com.example.commons.utils.PropertiesUtil;

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

        // 读取指令翻译
        Properties trans = PropertiesUtil.load(Path.of(dir, fileName));
        return refHotKeys.stream()
                .map(
                        x ->
                                new CmdHotKeyVO(
                                        x.getCmd(),
                                        x.getComments(),
                                        trans.getProperty(x.getCmd()),
                                        x.getHotKey()))
                .collect(Collectors.toList());
    }
}
