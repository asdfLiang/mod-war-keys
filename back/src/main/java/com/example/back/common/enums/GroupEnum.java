package com.example.back.common.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 配置分组
 *
 * @since 2023/4/15 11:13
 * @author by liangzj
 */
@Getter
@AllArgsConstructor
public enum GroupEnum {
    Shared(RaceEnum.Shared, "Shared Commands (attack, etc.)", "通用指令"),
    Human_Units(RaceEnum.Human, "Human Units & Abilities", "人类单位&技能"),
    Human_Bldgs(RaceEnum.Human, "Human Bldgs, Upgrades & Abilities", "人类建筑升级&技能"),
    Human_Heroes(RaceEnum.Human, "Human Heroes & Abilities", "人类英雄&技能"),
    Orc_Units(RaceEnum.Orc, "Orc Units & Abilities", "兽人单位&技能"),
    Orc_Bldgs(RaceEnum.Orc, "Orc Bldgs, Upgrades & Abilities", "兽人建筑升级&技能"),
    Orc_Heroes(RaceEnum.Orc, "Orc Heroes & Abilities", "兽人英雄&技能"),
    Night_Units(RaceEnum.Night, "Night Elf Units & Abilities", "暗夜精灵单位&技能"),
    Night_Bldgs(RaceEnum.Night, "Night Elf Bldgs, Upgrades & Abilities", "暗夜精灵建筑升级&技能"),
    Night_Heroes(RaceEnum.Night, "Night Elf Heroes & Abilities", "暗夜精灵英雄&技能"),
    ;

    private final RaceEnum raceEnum;

    private final String title;

    private final String desc;
}
