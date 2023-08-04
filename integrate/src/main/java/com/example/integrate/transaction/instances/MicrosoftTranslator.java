package com.example.integrate.transaction.instances;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.example.common.utils.HttpUtil;
import com.example.integrate.transaction.AbstractTranslator;
import com.example.integrate.transaction.Translator;
import com.example.integrate.transaction.enums.TranslationEngineEnum;

import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.*;

/**
 * @since 2023/4/14 20:56
 * @author by liangzj
 */
@Component
public class MicrosoftTranslator extends AbstractTranslator implements Translator {
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
    protected String jsonRequestBody(Map<String, String> params) {
        String text = params.get("text");

        List<Object> list = new ArrayList<>();
        HashMap<Object, Object> map = new HashMap<>();
        map.put("Text", text);
        list.add(map);

        return JSON.toJSONString(list);
    }

    @Override
    protected String analysisResponseBody(HttpResponse<String> response) {
        return JSONArray.parse(response.body())
                .getJSONObject(0)
                .getJSONArray("translations")
                .getJSONObject(0)
                .getString("text");
    }

    @Override
    public TranslationEngineEnum engine() {
        return TranslationEngineEnum.Microsoft;
    }

    @Override
    public String translate(String text, String from, String to) {
        HashMap<String, String> params = new HashMap<>();
        params.put("text", text);
        params.put("from", from);
        params.put("to", to);
        return postTranslate(params);
    }

    private String getAuthorization() {
        return "Bearer " + HttpUtil.get(authUrl, null, null).body();
    }

    //    public static void main(String[] args) {
    //        MicrosoftTranslator translator = new MicrosoftTranslator();
    //
    //        System.out.println(translator.getAuthorization());
    //
    //        System.out.println(translator.translate("こんにちは", null, LanguageEnum.ZH.name()));
    //        System.out.println(translator.translate("Hello World", null, LanguageEnum.ZH.name()));
    //    }
}
