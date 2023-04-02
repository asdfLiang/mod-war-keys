package com.example.back.api;
import com.example.back.model.CmdHotKeyVO;import java.util.List; /**
 * @since 2023/3/25 12:40
 * @author by liangzj
 */
public interface TranslationService {

    /** 初始化翻译文本 */
    void tryInitTranslationText(List<CmdHotKeyVO> hotKeys);

    /** 完善翻译，检查是否有未翻译的文本，如果有，进行翻译 */
    void perfectTranslation();
}
