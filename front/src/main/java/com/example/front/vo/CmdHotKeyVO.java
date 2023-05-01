package com.example.front.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @since 2023/5/1 9:25
 * @author by liangzj
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmdHotKeyVO {
    /** 指令 */
    private String cmd;

    /** 指令类型 */
    private String cmdTypeDesc;

    /** 指令说明 */
    private String cmdTranslation;

    /** 热键 */
    private String hotKey;
}
