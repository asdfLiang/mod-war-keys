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
public abstract class HttpTemplate {

    protected HttpResponse<String> get(Map<String, String> query) {
        return HttpUtil.get(uri(), headers(), query);
    }

    protected HttpResponse<String> post(Map<String, String> params) {
        return HttpUtil.post(uri(), headers(), preparedBody(params));
    }

    /** 请求uri */
    protected abstract String uri();

    /** 请求头信息 */
    protected abstract Map<String, String> headers();

    /**
     * 准备请求数据
     *
     * @param params 动态参数
     * @return 求体json
     */
    protected abstract String preparedBody(Map<String, String> params);

    /** 解析请求结果 */
    protected abstract String parseResponseBody(HttpResponse<String> response);
}
