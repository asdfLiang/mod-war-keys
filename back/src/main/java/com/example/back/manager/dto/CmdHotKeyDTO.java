package com.example.back.manager.dto;

import com.example.back.support.enums.CmdTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 快捷键展示对象
 *
 * @since 2023/3/24 22:22
 * @author by liangzj
 */
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CmdHotKeyDTO {

    /** 指令 */
    private String cmd;

    /** 指令说明 */
    private String comments;

    /** 指令类型说明 */
    private CmdTypeEnum cmdType;

    /** 指令说明翻译 */
    private String translation;

    /** 热键 */
    private String hotKey;
}
