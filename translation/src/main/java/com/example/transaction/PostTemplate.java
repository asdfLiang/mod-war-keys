package com.example.transaction;

import com.example.commons.utils.HttpUtil;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * post请求模板
 *
 * @since 2023/3/25 14:23
 * @author by liangzj
 */
public abstract class PostTemplate {

    protected HttpResponse<String> post(Map<String, String> params) {
        return HttpUtil.post(uri(), headers(), body(params));
    }

    /**
     * 请求uri
     *
     * @return
     */
    protected abstract String uri();

    /**
     * 请求头信息
     *
     * @return
     */
    protected abstract Map<String, String> headers();

    /**
     * 请求体
     *
     * @param params
     * @return
     */
    protected abstract String body(Map<String, String> params);

    /**
     * 解析请求结果
     *
     * @param responseBody
     * @return
     */
    protected abstract String parseResponseBody(String responseBody);
}
