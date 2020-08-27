package com.myretail.product.controller

import com.myretail.product.dto.PriceDto
import com.myretail.product.dto.ProductDto
import com.myretail.product.service.PriceService
import com.myretail.product.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification
import spock.lang.Unroll

class ProductControllerSpec extends Specification {

    PriceService mockPriceService = Mock()
    ProductService mockProductService = Mock()

    ProductController productController = new ProductController(
            mockPriceService,
            mockProductService)

    @Unroll
    def 'getProducts - product information found'() {
        given:
        long id = 101

        when:
        ResponseEntity<ProductDto> responseEntity = productController.getProducts(id)

        then:
        1 * mockProductService.getProduct(id) >> productDto
        0 * _

        and:
        responseEntity.body == productDto
        responseEntity.statusCode == HttpStatus.OK

        where:
        productDto                                                                        | _
        ProductDto.builder().name("foo").build()                                          | _
        ProductDto.builder().currentPrice(PriceDto.builder().build()).build()             | _
        ProductDto.builder().name("foo").currentPrice(PriceDto.builder().build()).build() | _
    }

    def 'getProducts - no product information found'() {
        given:
        long id = 101
        ProductDto productDto = ProductDto.builder()
                .id(id)
                .build()

        when:
        ResponseEntity<ProductDto> responseEntity = productController.getProducts(id)

        then:
        1 * mockProductService.getProduct(id) >> productDto
        0 * _

        and:
        !responseEntity.body
        responseEntity.statusCode == HttpStatus.NOT_FOUND
    }

    def 'putProducts - happy path'() {
        given:
        long id = 101
        PriceDto priceDto = PriceDto.builder()
                .value(BigDecimal.valueOf(11))
                .currencyCode("foo")
                .build()

        when:
        ResponseEntity<ProductDto> responseEntity = productController.putProducts(id, priceDto)

        then:
        1 * mockPriceService.savePrice(id, priceDto)
        0 * _

        and:
        !responseEntity.body
        responseEntity.statusCode == HttpStatus.OK
    }

    @Unroll
    def 'putProducts - bad request'() {
        given:
        long id = 101

        when:
        ResponseEntity<ProductDto> responseEntity = productController.putProducts(id, priceDto)

        then:
        0 * _

        and:
        !responseEntity.body
        responseEntity.statusCode == HttpStatus.BAD_REQUEST

        where:
        priceDto                                                 | _
        PriceDto.builder().value(BigDecimal.valueOf(11)).build() | _
        PriceDto.builder().currencyCode("foo").build()           | _
        PriceDto.builder().build()                               | _
        null                                                     | _
    }
}
