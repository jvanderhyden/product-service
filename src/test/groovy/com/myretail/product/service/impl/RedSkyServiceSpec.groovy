package com.myretail.product.service.impl

import com.myretail.product.dto.RedSkyDto
import org.springframework.web.client.RestTemplate
import spock.lang.Specification
import spock.lang.Unroll

class RedSkyServiceSpec extends Specification {

    RestTemplate mockRestTemplate = Mock()

    RedSkyServiceImpl redSkyService = new RedSkyServiceImpl(mockRestTemplate)

    def 'getProductName - happy path'() {
        given:
        long id = 101
        String title = "A Fine Product"
        RedSkyDto redSkyDto = new RedSkyDto(
                new RedSkyDto.Product(
                        new RedSkyDto.Item(
                                new RedSkyDto.ProductDescription(title))))

        when:
        Optional<String> productName = redSkyService.getProductName(id)

        then:
        1 * mockRestTemplate.getForObject("/v3/pdp/tcin/{id}", RedSkyDto.class, id) >> redSkyDto
        0 * _

        and:
        productName.isPresent()
        productName.get() == title
    }

    @Unroll
    def 'getProductName - response does not contain title'() {
        given:
        long id = 101

        when:
        Optional<String> productName = redSkyService.getProductName(id)

        then:
        1 * mockRestTemplate.getForObject(_ as String, RedSkyDto.class, id) >> dto
        0 * _

        and:
        productName.isEmpty()

        where:
        dto                                                                                              | _
        new RedSkyDto(new RedSkyDto.Product(new RedSkyDto.Item(new RedSkyDto.ProductDescription(null)))) | _
        new RedSkyDto(new RedSkyDto.Product(new RedSkyDto.Item(null)))                                   | _
        new RedSkyDto(new RedSkyDto.Product(null))                                                       | _
        new RedSkyDto(null)                                                                              | _
        null                                                                                             | _
    }
}
