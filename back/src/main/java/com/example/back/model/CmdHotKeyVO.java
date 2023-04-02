package com.example.back.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 快捷键展示对象
 *
 * @since 2023/3/24 22:22
 * @author by liangzj
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmdHotKeyVO {

    /** 指令 */
    private String cmd;

    /** 指令名称 */
    private String cmdName;

    /** 热键 */
    private String hotKey;
}
