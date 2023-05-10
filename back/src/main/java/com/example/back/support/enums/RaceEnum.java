package com.example.back.support.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @since 2023/5/7 19:25
 * @author by liangzj
 */
@Getter
@AllArgsConstructor
public enum RaceEnum {
    All(0, "全部"),
    Human(1, "人类"),
    Orc(2, "兽人"),
    Night_elf(3, "暗夜精灵"),
    Undead(4, "不死族");

    private final Integer race;
    private final String desc;
}
