package com.example.commons.utils;

import java.util.Arrays;
import java.util.Objects;

/**
 * @since 2023/3/25 1:06
 * @author by liangzj
 */
public final class StringUtil {

    private static final String BLANK = "";

    public static boolean isNotBlank(String string) {
        return !isBlank(string);
    }

    public static boolean isBlank(String string) {
        return Objects.isNull(string) || BLANK.equals(string.trim());
    }

    public static boolean noneBlank(String... strings) {
        return Arrays.stream(strings).allMatch(StringUtil::isNotBlank);
    }
}
