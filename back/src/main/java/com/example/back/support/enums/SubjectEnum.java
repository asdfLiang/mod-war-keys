package com.example.back.support.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 指令操作的主体
 *
 * @since 2023/5/8 21:57
 * @author by liangzj
 */
@Getter
@AllArgsConstructor
public enum SubjectEnum {
    Shared(0, "通用"),
    Units(1, "单位&技能"),
    Bldgs(2, "建筑升级&技能"),
    Heroes(3, "英雄&技能");

    private final Integer type;

    private final String desc;
}
