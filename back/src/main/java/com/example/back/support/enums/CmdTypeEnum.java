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
    Shared(0, All, SubjectEnum.Shared, "Shared Commands (attack, etc.)"),
    Human_Units(1, Human, SubjectEnum.Units, "Human Units & Abilities"),
    Human_Bldgs(2, Human, SubjectEnum.Bldgs, "Human Bldgs, Upgrades & Abilities"),
    Human_Heroes(3, Human, SubjectEnum.Heroes, "Human Heroes & Abilities"),
    Orc_Units(4, Orc, SubjectEnum.Units, "Orc Units & Abilities"),
    Orc_Bldgs(5, Orc, SubjectEnum.Bldgs, "Orc Bldgs, Upgrades & Abilities"),
    Orc_Heroes(6, Orc, SubjectEnum.Heroes, "Orc Heroes & Abilities"),
    Night_Units(7, Night_elf, SubjectEnum.Units, "Night Elf Units & Abilities"),
    Night_Bldgs(8, Night_elf, SubjectEnum.Bldgs, "Night Elf Bldgs, Upgrades & Abilities"),
    Night_Heroes(9, Night_elf, SubjectEnum.Heroes, "Night Elf Heroes & Abilities"),
    Undead_Units(10, Undead, SubjectEnum.Units, "Undead Units & Abilities"),
    Undead_Bldgs(11, Undead, SubjectEnum.Bldgs, "Undead Bldgs, Upgrades & Abilities"),
    Undead_Heroes(12, Undead, SubjectEnum.Heroes, "Undead Heroes & Abilities"),
    ;

    /** 指令类型 */
    private final Integer type;

    /** 指令所属种族 */
    private final RaceEnum race;

    /** 指令操作主体 */
    private final SubjectEnum subject;

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
