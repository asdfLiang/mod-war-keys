package com.example.back.support.enums;

import static com.example.back.support.enums.RaceEnum.*;

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
    Shared(0, All, UnitTypeEnum.Shared, "Shared Commands (attack, etc.)"),
    Human_Units(1, Human, UnitTypeEnum.Units, "Human Units & Abilities"),
    Human_Bldgs(2, Human, UnitTypeEnum.Bldgs, "Human Bldgs, Upgrades & Abilities"),
    Human_Heroes(3, Human, UnitTypeEnum.Heroes, "Human Heroes & Abilities"),
    Orc_Units(4, Orc, UnitTypeEnum.Units, "Orc Units & Abilities"),
    Orc_Bldgs(5, Orc, UnitTypeEnum.Bldgs, "Orc Bldgs, Upgrades & Abilities"),
    Orc_Heroes(6, Orc, UnitTypeEnum.Heroes, "Orc Heroes & Abilities"),
    Night_Units(7, Night_elf, UnitTypeEnum.Units, "Night Elf Units & Abilities"),
    Night_Bldgs(8, Night_elf, UnitTypeEnum.Bldgs, "Night Elf Bldgs, Upgrades & Abilities"),
    Night_Heroes(9, Night_elf, UnitTypeEnum.Heroes, "Night Elf Heroes & Abilities"),
    Undead_Units(10, Undead, UnitTypeEnum.Units, "Undead Units & Abilities"),
    Undead_Bldgs(11, Undead, UnitTypeEnum.Bldgs, "Undead Bldgs, Upgrades & Abilities"),
    Undead_Heroes(12, Undead, UnitTypeEnum.Heroes, "Undead Heroes & Abilities"),
    ;

    /** 指令类型 */
    private final Integer type;

    /** 指令所属种族 */
    private final RaceEnum race;

    /** 指令操作主体 */
    private final UnitTypeEnum unitType;

    /** 指令在配置文件中的注释标识 */
    private final String title;

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

    public static CmdTypeEnum from(RaceEnum race) {
        return Arrays.stream(CmdTypeEnum.values())
                .filter(typeEnum -> typeEnum.getRace() == race)
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("unknown race: " + race));
    }

    public static boolean isRace(int type, RaceEnum race) {
        CmdTypeEnum cmdType = from(type);
        return cmdType.getRace() == race;
    }
}
