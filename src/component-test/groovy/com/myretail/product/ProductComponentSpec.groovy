package com.myretail.product

import com.myretail.product.dto.PriceDto
import com.myretail.product.dto.ProductDto
import org.mockserver.model.HttpRequest
import org.mockserver.model.HttpResponse
import org.mockserver.model.MediaType

class ProductComponentSpec extends BaseComponentSpec {

    def 'products - happy path'() {
        given:
        long id = 101
        String productName = "foo"
        String currencyCode = "bar"
        BigDecimal value = BigDecimal.valueOf(11)
        PriceDto priceDto = PriceDto.builder()
                .value(value)
                .currencyCode(currencyCode)
                .build()

        and:
        restTemplate.put("/products/$id", priceDto)

        and:
        HttpRequest mockRequest = new HttpRequest()
                .withMethod("GET")
                .withPath("/v3/pdp/tcin/$id")
        HttpResponse mockResponse = new HttpResponse()
                .withStatusCode(200)
                .withBody("{\"product\":{\"item\":{\"product_description\":{\"title\":\"$productName\"}}}}")
                .withContentType(MediaType.APPLICATION_JSON)
        mockServerClient
                .when(mockRequest)
                .respond(mockResponse)

        when:
        ProductDto productDto = restTemplate.getForObject("/products/$id", ProductDto.class)

        then:
        productDto
        productDto.id == id
        productDto.name == productName
        productDto.currentPrice.value == value
        productDto.currentPrice.currencyCode == currencyCode
    }
}
