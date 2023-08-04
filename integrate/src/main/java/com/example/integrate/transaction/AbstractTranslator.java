package com.example.integrate.transaction;

import com.example.common.utils.ThreadUtil;

import java.net.http.HttpResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @since 2023/3/25 12:59
 * @author by liangzj
 */
public abstract class AbstractTranslator extends HttpTemplate implements Translator {

    protected String postTranslate(HashMap<String, String> params) {
        ThreadUtil.sleep(3);

        HttpResponse<String> response = post(params);

        return analysisResponseBody(response);
    }

    protected String getTranslate(Map<String, String> query) {
        return analysisResponseBody(get(query));
    }
}
