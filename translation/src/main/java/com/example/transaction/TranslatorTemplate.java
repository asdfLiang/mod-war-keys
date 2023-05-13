package com.example.transaction;

import com.example.commons.utils.ThreadUtil;

import org.apache.commons.lang3.StringUtils;

import java.net.http.HttpResponse;
import java.util.HashMap;

/**
 * 翻译请求模板
 *
 * @since 2023/3/25 12:59
 * @author by liangzj
 */
public abstract class TranslatorTemplate extends HttpTemplate implements Translator {

    @Override
    public String translate(String text, String from, String to) {
        if (StringUtils.isBlank(text)) {
            return "";
        }

        ThreadUtil.sleep(3);

        HashMap<String, String> params = new HashMap<>();
        params.put("text", text);
        params.put("from", from);
        params.put("to", to);

        HttpResponse<String> response = post(params);

        return parseResponseBody(response);
    }
}
