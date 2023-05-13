package com.example.back.support.constants;

/**
 * 配置内容固定标记，用来识别配置信息
 *
 * @since 2023/3/25 0:38
 * @author by liangzj
 */
public final class MarkConstant {
    /** 指令说明前缀 */
    public static final String COMMENTS_START = "// ";

    public static final String COMMENTS_START_REGEX = "^//[ ]{0,1}[a-zA-z].+$";

    /** 指令行前缀 */
    public static final String CMD_STARTS = "[";

    /** 热键行前缀 */
    public static final String HOTKEY_START = "Hotkey=";

    /** 自动执行 */
    public static final String UN_HOTKEY_START = "Unhotkey=";

    /** 升级技能热键 */
    public static final String RESEARCH_HOTKEY_START = "Researchhotkey=";

    public static final String UN_CMD_START = "Un";
    public static final String RES_CMD_START = "Res";
}
