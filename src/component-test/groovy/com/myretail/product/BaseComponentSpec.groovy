package com.myretail.product

import org.mockserver.client.MockServerClient
import org.springframework.boot.web.client.RestTemplateBuilder
import org.springframework.web.client.RestTemplate
import org.testcontainers.containers.DockerComposeContainer
import org.testcontainers.containers.wait.strategy.Wait
import org.testcontainers.spock.Testcontainers
import spock.lang.Specification

@Testcontainers
class BaseComponentSpec extends Specification {

    RestTemplate restTemplate
    MockServerClient mockServerClient

    DockerComposeContainer composeContainer = new DockerComposeContainer(new File("src/component-test/resources/docker-compose-component-test.yml"))
            .withExposedService("product_1", 8080)
            .withExposedService("mockserver_1", 1080, Wait.forHttp("/health"))

    void setup() {
        String productHost = composeContainer.getServiceHost("product_1", 8080)
        Integer productPort = composeContainer.getServicePort("product_1", 8080)
        restTemplate = new RestTemplateBuilder()
                .rootUri("http://$productHost:$productPort")
                .build()

        String mockServerHost = composeContainer.getServiceHost("mockserver_1", 1080)
        Integer mockServerPort = composeContainer.getServicePort("mockserver_1", 1080)
        mockServerClient = new MockServerClient(mockServerHost, mockServerPort)
    }
}
