package com.myretail.product.service.impl

import com.myretail.product.dto.PriceDto
import com.myretail.product.dto.ProductDto
import com.myretail.product.exception.EntityNotFoundException
import com.myretail.product.exception.ValidationException
import com.myretail.product.model.Price
import com.myretail.product.repository.PriceRepository
import com.myretail.product.service.RedSkyService
import spock.lang.Specification

class ProductServiceSpec extends Specification {

    PriceRepository mockPriceRepository = Mock()
    RedSkyService mockRedSkyService = Mock()

    ProductServiceImpl productService = new ProductServiceImpl(
            mockPriceRepository,
            mockRedSkyService)

    def 'find - happy path'() {
        given:
        long id = 101
        BigDecimal value = BigDecimal.valueOf(11)
        String currencyCode = "USD"
        Price price = Price.builder()
                .id(id)
                .value(value)
                .currencyCode(currencyCode)
                .build()
        String productName = "A Fine Product"

        when:
        ProductDto productDto = productService.find(id)

        then:
        1 * mockPriceRepository.findById(id) >> Optional.of(price)
        1 * mockRedSkyService.getProductName(id) >> Optional.of(productName)
        0 * _

        and:
        productDto
        productDto.id == id
        productDto.name == productName
        productDto.currentPrice
        productDto.currentPrice.value == value
        productDto.currentPrice.currencyCode == currencyCode
    }

    def 'find - product name does not exist'() {
        given:
        long id = 101
        BigDecimal value = BigDecimal.valueOf(11)
        String currencyCode = "USD"
        Price price = Price.builder()
                .id(id)
                .value(value)
                .currencyCode(currencyCode)
                .build()

        when:
        productService.find(id)

        then:
        1 * mockPriceRepository.findById(id) >> Optional.of(price)
        1 * mockRedSkyService.getProductName(id) >> Optional.empty()
        0 * _

        and:
        thrown(EntityNotFoundException)
    }

    def 'find - price does not exist'() {
        given:
        long id = 101

        when:
        productService.find(id)

        then:
        1 * mockPriceRepository.findById(id) >> Optional.empty()
        0 * _

        and:
        thrown(EntityNotFoundException)
    }

    def 'save - happy path'() {
        given:
        long id = 101
        BigDecimal value = BigDecimal.valueOf(11)
        String currencyCode = "USD"
        PriceDto priceDto = PriceDto.builder()
                .value(value)
                .currencyCode(currencyCode)
                .build()
        Price price

        when:
        productService.save(id, priceDto)

        then:
        1 * mockPriceRepository.save({ price = it })
        0 * _

        and:
        price
        price.id == id
        price.value == value
        price.currencyCode == currencyCode
    }

    def 'transform - happy path'() {
        given:
        BigDecimal value = BigDecimal.valueOf(11)
        String currencyCode = "USD"
        Price price = Price.builder()
                .value(value)
                .currencyCode(currencyCode)
                .build()

        when:
        PriceDto priceDto = productService.transform(price)

        then:
        0 * _

        and:
        priceDto
        priceDto.value == value
        priceDto.currencyCode == currencyCode
    }

    def 'validatePrice - missing priceDto'() {
        when:
        productService.validatePrice(null)

        then:
        0 * _

        and:
        thrown(ValidationException)
    }

    def 'validatePrice - missing value'() {
        given:
        PriceDto priceDto = PriceDto.builder()
                .build()

        when:
        productService.validatePrice(priceDto)

        then:
        0 * _

        and:
        thrown(ValidationException)
    }

    def 'validatePrice - missing currencyCode'() {
        given:
        PriceDto priceDto = PriceDto.builder()
                .value(BigDecimal.valueOf(11))
                .build()

        when:
        productService.validatePrice(priceDto)

        then:
        0 * _

        and:
        thrown(ValidationException)
    }

    def 'validatePrice - happy path'() {
        given:
        PriceDto priceDto = PriceDto.builder()
                .value(BigDecimal.valueOf(11))
                .currencyCode("USD")
                .build()

        when:
        productService.validatePrice(priceDto)

        then:
        0 * _

        and:
        noExceptionThrown()
    }
}
