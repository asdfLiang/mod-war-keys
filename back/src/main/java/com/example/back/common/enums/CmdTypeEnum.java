package com.example.back.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 指令类型
 *
 * @since 2023/3/23 1:40
 * @author by liangzj
 */
@Getter
@AllArgsConstructor
public enum CmdTypeEnum {
    Shared(0, "通用"),
    Hero(1, "英雄"),
    Units(2, "单位"),
    Skill(3, "技能"),
    ;

    private Integer code;
    private String desc;
}
