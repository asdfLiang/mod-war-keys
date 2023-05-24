package com.example.service.support.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 具体单位的名称
 *
 * @since 2023/5/14 8:20
 * @author by liangzj
 */
@Getter
@AllArgsConstructor
public enum UnitNameEnum {
    okod("okod", "科多兽"),
    otbr("otbr", "巨魔蝙蝠骑士");

    private final String unitName;

    private final String translation;
}
