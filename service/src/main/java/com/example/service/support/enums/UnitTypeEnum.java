package com.example.service.support.enums;

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
public enum UnitTypeEnum {
    Shared(0, "通用"),
    // 单位&技能
    Units(1, "兵种"),
    // 建筑升级&技能
    Bldgs(2, "建筑"),
    // 英雄&技能
    Heroes(3, "英雄");

    private final Integer type;

    private final String desc;
}
