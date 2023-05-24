package com.example.integrate.transaction.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 翻译配置
 *
 * @since 2023/4/22 23:45
 * @author by liangzj
 */
@Configuration
public class TranslationConfig {

    @Value("${baidu.translation.app.id}")
    public String baiduAppId;

    @Value("${baidu.translation.secret.key}")
    public String baiduSecretKey;

    @Value("${baidu.translation.salt}")
    public String baiduSalt;
}
