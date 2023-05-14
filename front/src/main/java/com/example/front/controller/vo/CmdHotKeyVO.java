package com.example.front.controller.vo;

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

    /** 种族 */
    private String raceDesc;

    /** 单位类型 */
    private String unitTypeDesc;

    /** 指令说明 */
    private String cmdTranslation;

    /** 热键 */
    private String hotKey;
}
