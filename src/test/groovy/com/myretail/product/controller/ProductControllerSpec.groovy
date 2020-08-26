package com.myretail.product.controller

import com.myretail.product.dto.PriceDto
import com.myretail.product.dto.ProductDto
import com.myretail.product.service.ProductService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import spock.lang.Specification


class ProductControllerSpec extends Specification {

    ProductService mockProductService = Mock()

    ProductController productController = new ProductController(mockProductService)

    def 'getProducts - happy path'() {
        given:
        long id = 101
        ProductDto productDto = ProductDto.builder()
                .id(id)
                .build()

        when:
        ResponseEntity<ProductDto> responseEntity = productController.getProducts(id)

        then:
        1 * mockProductService.find(id) >> productDto
        0 * _

        and:
        responseEntity.body == productDto
        responseEntity.statusCode == HttpStatus.OK
    }

    def 'putProducts - happy path'() {
        given:
        long id = 101
        PriceDto priceDto = PriceDto.builder()
                .value(BigDecimal.valueOf(11))
                .build()

        when:
        ResponseEntity<ProductDto> responseEntity = productController.putProducts(id, priceDto)

        then:
        1 * mockProductService.save(id, priceDto)
        0 * _

        and:
        !responseEntity.body
        responseEntity.statusCode == HttpStatus.OK
    }
}
