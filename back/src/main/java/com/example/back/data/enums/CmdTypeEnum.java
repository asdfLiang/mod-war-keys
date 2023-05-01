package com.example.back.data.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

/**
 * 指令类型
 *
 * @since 2023/4/15 11:13
 * @author by liangzj
 */
@Getter
@AllArgsConstructor
public enum CmdTypeEnum {
    Shared(0, "Shared Commands (attack, etc.)", "通用指令"),
    Human_Units(1, "Human Units & Abilities", "人类单位&技能"),
    Human_Bldgs(2, "Human Bldgs, Upgrades & Abilities", "人类建筑升级&技能"),
    Human_Heroes(3, "Human Heroes & Abilities", "人类英雄&技能"),
    Orc_Units(4, "Orc Units & Abilities", "兽人单位&技能"),
    Orc_Bldgs(5, "Orc Bldgs, Upgrades & Abilities", "兽人建筑升级&技能"),
    Orc_Heroes(6, "Orc Heroes & Abilities", "兽人英雄&技能"),
    Night_Units(7, "Night Elf Units & Abilities", "暗夜精灵单位&技能"),
    Night_Bldgs(8, "Night Elf Bldgs, Upgrades & Abilities", "暗夜精灵建筑升级&技能"),
    Night_Heroes(9, "Night Elf Heroes & Abilities", "暗夜精灵英雄&技能"),
    ;

    private final Integer type;

    private final String title;

    private final String desc;

    public static CmdTypeEnum fromTitle(String title) {
        return Arrays.stream(CmdTypeEnum.values())
                .filter(typeEnum -> title.contains(typeEnum.getTitle()))
                .findFirst()
                .orElse(null);
    }

    public static CmdTypeEnum from(int type) {
        return Arrays.stream(CmdTypeEnum.values())
                .filter(typeEnum -> typeEnum.getType().equals(type))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown type: " + type));
    }
}
