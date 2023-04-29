package com.example.transaction.instances;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.example.commons.utils.HttpUtil;
import com.example.transaction.Translator;
import com.example.transaction.TranslatorTemplate;
import com.example.transaction.enums.LanguageEnum;
import com.example.transaction.enums.TranslatorEnum;

import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.*;

/**
 * @since 2023/4/14 20:56
 * @author by liangzj
 */
@Component
public class MicrosoftTranslator extends TranslatorTemplate implements Translator {
    private static final String authUrl = "https://edge.microsoft.com/translate/auth";

    private static String authorization = null;

    @Override
    protected String uri() {
        return "https://api-edge.cognitive.microsofttranslator.com/translate?from=&to=ZH&api-version=3.0&includeSentenceLength=true";
    }

    @Override
    protected Map<String, String> headers() {
        authorization = Optional.ofNullable(authorization).orElseGet(this::getAuthorization);
        return new HashMap<>() {
            {
                put("authorization", authorization);
                put("content-type", "application/json");
            }
        };
    }

    @Override
    protected String preparedBody(Map<String, String> params) {
        String text = params.get("text");

        List<Object> list = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("Text", text);
        list.add(map);

        return JSON.toJSONString(list);
    }

    @Override
    protected String parseResponseBody(HttpResponse<String> response) {
        return JSONArray.parse(response.body())
                .getJSONObject(0)
                .getJSONArray("translations")
                .getJSONObject(0)
                .getString("text");
    }

    @Override
    public TranslatorEnum type() {
        return TranslatorEnum.Microsoft;
    }

    private String getAuthorization() {
        return "Bearer " + HttpUtil.get(authUrl, null, null).body();
    }

    public static void main(String[] args) {
        MicrosoftTranslator translator = new MicrosoftTranslator();

        System.out.println(translator.getAuthorization());

        System.out.println(translator.translate("こんにちは", null, LanguageEnum.ZH.name()));
        System.out.println(translator.translate("Hello World", null, LanguageEnum.ZH.name()));
    }
}
