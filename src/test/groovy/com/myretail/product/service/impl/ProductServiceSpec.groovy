package com.myretail.product.service.impl

import com.myretail.product.dto.PriceDto
import com.myretail.product.dto.ProductDto
import com.myretail.product.service.PriceService
import com.myretail.product.service.RedSkyService
import spock.lang.Specification

import java.util.concurrent.CompletableFuture

class ProductServiceSpec extends Specification {

    PriceService mockPriceService = Mock()
    RedSkyService mockRedSkyService = Mock()

    ProductServiceImpl productService = new ProductServiceImpl(
            mockPriceService,
            mockRedSkyService)

    def 'getProduct - happy path'() {
        given:
        long id = 101
        PriceDto priceDto = PriceDto.builder()
                .value(BigDecimal.valueOf(11))
                .build()
        String productName = "foo"

        when:
        ProductDto productDto = productService.getProduct(id)

        then:
        1 * mockPriceService.getPrice(id) >> CompletableFuture.completedFuture(priceDto)
        1 * mockRedSkyService.getProductName(id) >> CompletableFuture.completedFuture(productName)
        0 * _

        and:
        productDto
        productDto.id == id
        productDto.name == productName
        productDto.currentPrice == priceDto
    }

    def 'getProduct - exceptions for price and productName futures'() {
        given:
        long id = 101

        when:
        ProductDto productDto = productService.getProduct(id)

        then:
        1 * mockPriceService.getPrice(id) >> CompletableFuture.failedFuture(new RuntimeException())
        1 * mockRedSkyService.getProductName(id) >> CompletableFuture.failedFuture(new RuntimeException())
        0 * _

        and:
        productDto
        productDto.id == id
        !productDto.name
        !productDto.currentPrice
    }
}
