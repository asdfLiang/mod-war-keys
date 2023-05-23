package com.example.back.support.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * @since 2023/5/23 22:37
 * @author by liangzj
 */
public enum EnvEnum {
    dev,
    prod;

    public static EnvEnum from(String env) {
        if (StringUtils.isBlank(env)) {
            return dev;
        }

        return EnvEnum.valueOf(env);
    }
}
