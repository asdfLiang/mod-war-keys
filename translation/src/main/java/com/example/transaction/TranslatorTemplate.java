package com.example.transaction;

import com.example.commons.utils.StringUtil;

import java.net.http.HttpResponse;
import java.util.HashMap;

/**
 * 翻译请求模板
 *
 * @since 2023/3/25 12:59
 * @author by liangzj
 */
public abstract class TranslatorTemplate extends PostTemplate implements Translator {

    @Override
    public String translate(String text, String from, String to) {
        if (StringUtil.isBlank(text)) {
            return "";
        }

        HashMap<String, String> params = new HashMap<>();
        params.put("text", text);
        params.put("from", from);
        params.put("to", to);

        HttpResponse<String> response = post(params);

        return parseResponseBody(response.body());
    }
}
