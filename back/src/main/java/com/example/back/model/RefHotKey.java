package com.example.back.model;

import com.example.commons.utils.StringUtil;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;

/**
 * 原始热键信息
 *
 * @since 2023/3/24 23:28
 * @author by liangzj
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RefHotKey {

    /** 行号 */
    private Integer row;

    /** 指令 */
    private String cmd;

    /** 指令类型 */
    private Integer cmdType;

    /** 指令说明 */
    private String comments;

    /** 热键 */
    private String hotKey;

    /**
     * 检查并返回当前对象
     *
     * @return
     */
    public RefHotKey require() {
        if (Objects.nonNull(row) && StringUtil.isNotBlank(cmd)) {
            return this;
        }
        throw new RuntimeException("配置文件格式错误！热键信息：" + this);
    }
}
