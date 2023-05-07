package com.example.dal.entity;

import lombok.Data;

/**
 * 指令快捷键按钮
 *
 * @since 2023/3/20 22:13
 * @author by liangzj
 */
@Data
public class CmdHotKeyDO {

    private Long id;

    /** 行号 */
    private Integer row;

    /** 指令 */
    private String cmd;

    /** 热键 */
    private String hotKey;
}
