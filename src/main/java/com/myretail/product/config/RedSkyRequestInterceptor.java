package com.myretail.product.config;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;

@AllArgsConstructor
public class RedSkyRequestInterceptor implements ClientHttpRequestInterceptor {

    private final String excludes;
    private final String key;

    @Override
    public ClientHttpResponse intercept(HttpRequest request, byte[] body, ClientHttpRequestExecution execution) throws IOException {
        URI uri = UriComponentsBuilder.fromHttpRequest(request)
                .queryParam("excludes", excludes)
                .queryParam("key", key)
                .build()
                .toUri();

        HttpRequest modifiedRequest = new HttpRequest() {
            @Override
            public String getMethodValue() {
                return request.getMethodValue();
            }

            @Override
            public URI getURI() {
                return uri;
            }

            @Override
            public HttpHeaders getHeaders() {
                return request.getHeaders();
            }
        };

        return execution.execute(modifiedRequest, body);
    }
}
