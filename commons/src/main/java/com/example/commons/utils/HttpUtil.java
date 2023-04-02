package com.example.commons.utils;

import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * @since 2023/3/25 13:43
 * @author by liangzj
 */
public class HttpUtil {

    /**
     * 执行post请求
     *
     * @param uri
     * @param headers
     * @param body
     * @return
     */
    public static HttpResponse<String> post(String uri, Map<String, String> headers, String body) {
        try {
            // 构建请求参数
            HttpRequest.Builder builder = HttpRequest.newBuilder();
            builder.uri(URI.create(uri));
            if (!CollectionUtils.isEmpty(headers)) {
                headers.entrySet()
                        .forEach(header -> builder.header(header.getKey(), header.getValue()));
            }
            HttpRequest request = builder.POST(HttpRequest.BodyPublishers.ofString(body)).build();

            // 请求
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
