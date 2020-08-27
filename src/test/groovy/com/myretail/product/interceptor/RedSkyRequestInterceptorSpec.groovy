package com.myretail.product.interceptor

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpRequest
import org.springframework.http.client.ClientHttpRequestExecution
import spock.lang.Specification

class RedSkyRequestInterceptorSpec extends Specification {

    def 'intercept'() {
        given:
        String excludes = "foo"
        String key = "bar"
        RedSkyRequestInterceptor redSkyRequestInterceptor = new RedSkyRequestInterceptor(excludes, key)

        and:
        String body = '{"baz":0}'
        String uri = "http://qux.com"
        HttpRequest request = Mock()
        ClientHttpRequestExecution execution = Mock()
        HttpRequest modifiedRequest

        when:
        redSkyRequestInterceptor.intercept(request, body.bytes, execution)

        then:
        1 * request.getURI() >> new URI(uri)
        1 * request.getHeaders() >> new HttpHeaders()
        1 * execution.execute({ modifiedRequest = it }, body.bytes)
        0 * _

        and:
        modifiedRequest
        modifiedRequest.URI.toString() == "$uri?excludes=$excludes&key=$key"
    }
}
