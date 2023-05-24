package com.example.dal.entity;

import org.apache.commons.lang3.StringUtils;

import java.util.Objects;

/**
 * 原始热键信息
 *
 * @since 2023/3/24 23:28
 * @author by liangzj
 */
public record RefHotKey(Integer row, String cmd, Integer cmdType, String comments, String hotKey) {

    /** 检查并返回当前对象 */
    public RefHotKey require() {
        if (Objects.nonNull(row) && Objects.nonNull(cmdType) && StringUtils.isNotBlank(cmd)) {
            return this;
        }
        throw new IllegalArgumentException("配置文件格式错误！热键信息：" + this);
    }
}
