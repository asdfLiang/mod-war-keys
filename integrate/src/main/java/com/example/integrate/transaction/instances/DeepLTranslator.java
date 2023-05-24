package com.example.integrate.transaction.instances;

import com.alibaba.fastjson2.JSON;
import com.example.integrate.transaction.Translator;
import com.example.integrate.transaction.TranslatorTemplate;
import com.example.integrate.transaction.enums.TranslatorEnum;

import org.springframework.stereotype.Component;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 2023/3/25 3:37
 * @author by liangzj
 */
@Component
public class DeepLTranslator extends TranslatorTemplate implements Translator {

    private static final String URL = "https://www2.deepl.com/jsonrpc?method=LMT_handle_jobs";

    @Override
    public TranslatorEnum type() {
        return TranslatorEnum.DeepL;
    }

    @Override
    protected String uri() {
        return URL;
    }

    @Override
    protected Map<String, String> headers() {
        return new HashMap<>() {
            {
                put("content-type", "application/json");
            }
        };
    }

    @Override
    protected String preparedBody(Map<String, String> params) {
        String text = params.get("text");
        String to = params.get("to");

        return String.format(format(), text, to, System.currentTimeMillis());
    }

    @Override
    protected String parseResponseBody(HttpResponse<String> response) {
        return JSON.parseObject(response.body())
                .getJSONObject("result")
                .getJSONArray("translations")
                .getJSONObject(0)
                .getJSONArray("beams")
                .getJSONObject(0)
                .getJSONArray("sentences")
                .getJSONObject(0)
                .getString("text");
    }

    //    public static void main(String[] args) {
    //        Translator translator = new DeepLTranslator();
    //
    //        System.out.println(translator.translate("こんにちは", null, LanguageEnum.ZH.name()));
    //        System.out.println(translator.translate("Hello World", null, LanguageEnum.ZH.name()));
    //    }

    private String format() {
        return """
            {
                "jsonrpc": "2.0",
                "method": "LMT_handle_jobs",
                "params": {
                    "jobs": [
                        {
                            "kind": "default",
                            "sentences": [
                                {
                                    "text": "%s",
                                    "id": 0,
                                    "prefix": ""
                                }
                            ],
                            "raw_en_context_before": [],
                            "raw_en_context_after": [],
                            "preferred_num_beams": 4,
                            "quality": "fast"
                        }
                    ],
                    "lang": {
                        "preference": {
                            "weight": {
                                "DE": 0.1154,
                                "EN": 0.27221,
                                "ES": 0.08506,
                                "FR": 0.11614,
                                "IT": 0.05017,
                                "JA": 15.37199,
                                "NL": 0.0467,
                                "PL": 0.05298,
                                "PT": 0.0397,
                                "RU": 0.03268,
                                "ZH": 0.20528,
                                "BG": 0.01722,
                                "CS": 0.02964,
                                "DA": 0.03435,
                                "EL": 0.02054,
                                "ET": 0.0199,
                                "FI": 0.02529,
                                "HU": 0.02235,
                                "ID": 0.02088,
                                "LV": 0.02036,
                                "LT": 0.01783,
                                "RO": 0.02132,
                                "SK": 0.02541,
                                "SL": 0.02527,
                                "SV": 0.06046,
                                "TR": 0.0234,
                                "UK": 0.01976,
                                "KO": 0.01929,
                                "NB": 0.02446
                            },
                            "default": "default"
                        },
                        "source_lang_user_selected": "auto",
                        "target_lang": "%s"
                    },
                    "priority": -1,
                    "commonJobParams": {
                        "mode": "translate",
                        "browserType": 1
                    },
                    "timestamp": %d
                },
                "id": 74870015
            }
            """;
    }
}
