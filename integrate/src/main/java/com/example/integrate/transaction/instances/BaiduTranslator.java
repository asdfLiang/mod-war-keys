package com.example.integrate.transaction.instances;

import com.alibaba.fastjson2.JSON;
import com.example.integrate.transaction.AbstractTranslator;
import com.example.integrate.transaction.Translator;
import com.example.integrate.transaction.config.TranslationConfig;
import com.example.integrate.transaction.enums.TranslationEngineEnum;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * 百度翻译
 *
 * @since 2023/3/25 2:36
 * @author by liangzj
 */
@Component
public class BaiduTranslator extends AbstractTranslator implements Translator {
    private final String contentType = "application/x-www-form-urlencoded";

    @Autowired private TranslationConfig translationConfig;

    @Override
    public TranslationEngineEnum engine() {
        return TranslationEngineEnum.Baidu;
    }

    @Override
    public String translate(String text, String from, String to) {
        Map<String, String> query = new HashMap<>();
        query.put("q", text);
        query.put("from", "auto");
        query.put("to", to);
        query.put("appid", translationConfig.baiduAppId);
        query.put("salt", "1435660288");
        query.put("sign", sign(query));

        return getTranslate(query);
    }

    @Override
    protected String uri() {
        return "https://api.fanyi.baidu.com/api/trans/vip/translate";
    }

    @Override
    protected Map<String, String> headers() {
        return new HashMap<>() {
            {
                put("Content-Type", contentType);
            }
        };
    }

    @Override
    protected String jsonRequestBody(Map<String, String> params) {
        return null;
    }

    @Override
    protected String analysisResponseBody(HttpResponse<String> response) {
        return JSON.parseObject(response.body())
                .getJSONArray("trans_result")
                .getJSONObject(0)
                .getString("dst");
    }

    private String sign(Map<String, String> body) {
        String sign =
                body.get("appid")
                        + body.get("q")
                        + body.get("salt")
                        + translationConfig.baiduSecretKey;

        return DigestUtils.md5DigestAsHex(sign.getBytes());
    }

    //    public static void main(String[] args) {
    //        Translator translator = new BaiduTranslator();
    //
    //        System.out.println(
    //
    // DigestUtils.md5DigestAsHex("2015063000000001apple143566028812345678".getBytes()));
    //
    //        System.out.println(translator.translate("apple", null, "zh"));
    //        System.out.println(translator.translate("こんにちは", null, "zh"));
    //        //        System.out.println(translator.translate("Hello World", null, "zh"));
    //    }
}
