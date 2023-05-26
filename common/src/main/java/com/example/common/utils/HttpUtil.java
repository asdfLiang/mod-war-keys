package com.example.common.utils;

import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;
import java.util.stream.Collectors;

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
                headers.forEach(builder::header);
            }
            HttpRequest request = builder.POST(HttpRequest.BodyPublishers.ofString(body)).build();

            // 请求
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 执行post请求
     *
     * @param uri
     * @param headers
     * @param params
     * @return
     */
    public static HttpResponse<String> get(
            String uri, Map<String, String> headers, Map<String, String> params) {
        try {
            // 构建请求参数
            HttpRequest.Builder builder = HttpRequest.newBuilder();
            if (!CollectionUtils.isEmpty(params)) {
                String query =
                        params.entrySet().stream()
                                .map(entry -> entry.getKey() + "=" + entry.getValue())
                                .collect(Collectors.joining("&"));
                uri = uri + "?" + query;
            }
            builder.uri(URI.create(uri));
            // header
            if (!CollectionUtils.isEmpty(headers)) {
                headers.forEach(builder::header);
            }
            HttpRequest request = builder.GET().build();

            // 请求
            return HttpClient.newHttpClient().send(request, HttpResponse.BodyHandlers.ofString());
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
