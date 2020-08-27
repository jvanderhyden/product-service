package com.myretail.product.config

import org.springframework.http.client.InterceptingClientHttpRequestFactory
import org.springframework.web.client.RestTemplate
import spock.lang.Specification

class RedSkyConfigSpec extends Specification {

    def 'restTemplate - happy path'() {
        given:
        String baseUrl = "foo"
        String excludes = "bar"
        String key = "baz"

        RedSkyConfig redSkyConfig = new RedSkyConfig(
                baseUrl: baseUrl,
                excludes: excludes,
                key: key)

        when:
        RestTemplate restTemplate = redSkyConfig.restTemplate()

        then:
        restTemplate.uriTemplateHandler.rootUri == baseUrl

        and:
        InterceptingClientHttpRequestFactory requestFactory = restTemplate.requestFactory
        requestFactory.interceptors.size() == 1
        requestFactory.interceptors[0].key == key
        requestFactory.interceptors[0].excludes == excludes
    }
}
