package com.myretail.product.config;

import com.myretail.product.interceptor.RedSkyRequestInterceptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

@Configuration
public class RedSkyConfig {

    @Value("${redsky.base-url}")
    private String baseUrl;

    @Value("${redsky.connect-timeout}")
    private int connectTimeout;

    @Value("${redsky.read-timeout}")
    private int readTimeout;

    @Value("${redsky.excludes}")
    private String excludes;

    @Value("${redsky.key}")
    private String key;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplateBuilder()
                .rootUri(baseUrl)
                .setConnectTimeout(Duration.of(connectTimeout, ChronoUnit.MILLIS))
                .setReadTimeout(Duration.of(readTimeout, ChronoUnit.MILLIS))
                .additionalInterceptors(new RedSkyRequestInterceptor(excludes, key))
                .build();
    }
}
