package com.example.transaction.instances;

import com.alibaba.fastjson2.JSON;
import com.example.transaction.Translator;
import com.example.transaction.TranslatorTemplate;
import com.example.transaction.enums.TranslatorEnum;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 百度翻译
 *
 * @since 2023/3/25 2:36
 * @author by liangzj
 */
@Component
public class BaiduTranslator extends TranslatorTemplate implements Translator {

    private String uri = "https://fanyi.baidu.com/v2transapi?from=en&to=zh";
    private String param =
            "from=en&to=zh&transtype=realtime&simple_means_flag=3&sign=501406.214447&token=3221b56e04fb394db2e0729ce1eacb80&domain=common";

    private String acsToken =
            "1679683091954_1679683097307_6BQ3MT4PzC1SEOyV9Dyu8FxHzgSN8xiUHaee0GVAJnHxX6CwoHiSVF102ePR5+b2bbsahgLNQ5mShWLIs631CvNQeTk8zeN2synYhRMgK1bFOPjU2J3rXFqtlQ4syjhZNheMZC34Y6MXBTec+Okj7Hu/Hh9AMm3c08YnshCWDh1GgcMbZLN6uUulaub78Qu9lvl8YWfKTq8Yz9T6fG5Z27Mu1uwqFuI5m6YpLCeaI7UhBOGEeVYammlzWMj4Fk2kOws7OAPJlMGPPx78TKJ1ryOcR5sdN7OyBB5wakwO1OkTmKK/GBLYNb2Jlt/21lTRtpqsSMHFEDRSUZ0PyTDOxtusZcGZvN56IijIevBIIlU=";

    private String cookie =
            "BIDUPSID=80D5AC2C719C4592C9F2A8347952BBDE; PSTM=1592834907; REALTIME_TRANS_SWITCH=1; HISTORY_SWITCH=1; FANYI_WORD_SWITCH=1; SOUND_SPD_SWITCH=1; SOUND_PREFER_SWITCH=1; __yjs_duid=1_34a1e2aaedfbcba42b9f176d1b60de2c1618314909395; BAIDUID=3D022C6A15E50A15E4E18642A9C7461A:FG=1; APPGUIDE_10_0_2=1; BDUSS=gzOThTNS12M3hVc0pLcWxjWWVuRWgtTUNHSW1CM2FyM3JxZHJRMzNzTU5BV2xqSVFBQUFBJCQAAAAAAAAAAAEAAAA~9gEVMTMwMDI3MjkzNwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA10QWMNdEFjRH; BDUSS_BFESS=gzOThTNS12M3hVc0pLcWxjWWVuRWgtTUNHSW1CM2FyM3JxZHJRMzNzTU5BV2xqSVFBQUFBJCQAAAAAAAAAAAEAAAA~9gEVMTMwMDI3MjkzNwAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA10QWMNdEFjRH; BDORZ=B490B5EBF6F3CD402E515D22BCDA1598; delPer=0; PSINO=5; H_PS_PSSID=38185_36561_38410_38368_38308_37862_38468_38171_38290_38376_38262_37938_38343_37900_26350_38421_38281_37881; BAIDUID_BFESS=3D022C6A15E50A15E4E18642A9C7461A:FG=1; BA_HECTOR=0l8h84ag2h840la1a48la4eh1i1rrfj1m; ZFY=Ywi:Aiq:AaoFHz0dyfKMh2WLIFcXotJvQ:AE76WMa0WGzk:C; Hm_lvt_64ecd82404c51e03dc91cb9e8c025574=1678502900,1678503836,1679241946,1679683088; Hm_lpvt_64ecd82404c51e03dc91cb9e8c025574=1679683088; ab_sr=1.0.1_M2FmODMxYmZiZTQ1MDdmNzJkZjcyOWVjMjJjNjA5Y2U4MjkyZDhlNzM1YjRhNGY2N2Y4ZDI5YWM4MDhkOTkxYzkyNDkxYjA1ZGY5Mzc5OWQxYzMzNmRjMWE0YTAxYjk5ZDYzMzNhYWE5NmQ3NTA0ZTAwOGU1MmI4YWI5NGRiZjlmYTU2MzZjZTliMTExYmU1N2M4NmMyNGMwNjljMzIyOWExZDA1MDkzODk2ZmRjNDIyY2Q4ZmJmODQwNzY0ZTA3";

    private String contentType = "application/x-www-form-urlencoded; charset=UTF-8";

    @Override
    public TranslatorEnum type() {
        return TranslatorEnum.Baidu;
    }

    @Override
    protected String uri() {
        return uri;
    }

    @Override
    protected Map<String, String> headers() {
        return new HashMap<>() {
            {
                put("Acs-Token", acsToken);
                put("Cookie", cookie);
                put("Content-Type", contentType);
            }
        };
    }

    @Override
    protected String body(Map<String, String> params) {
        String text = params.get("text");

        return param + "&query=" + text;
    }

    @Override
    protected String parseResponseBody(String responseBody) {
        return JSON.parseObject(responseBody)
                .getJSONObject("trans_result")
                .getJSONArray("data")
                .getJSONObject(0)
                .getString("dst");
    }
}
