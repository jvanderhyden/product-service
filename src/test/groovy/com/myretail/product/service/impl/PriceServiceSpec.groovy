package com.myretail.product.service.impl

import com.myretail.product.dto.PriceDto
import com.myretail.product.exception.ValidationException
import com.myretail.product.model.Price
import com.myretail.product.repository.PriceRepository
import spock.lang.Specification

import java.util.concurrent.CompletableFuture

class PriceServiceSpec extends Specification {

    PriceRepository mockPriceRepository = Mock()

    PriceServiceImpl priceService = new PriceServiceImpl(mockPriceRepository)

    def 'getPrice - happy path'() {
        given:
        long id = 101
        BigDecimal value = BigDecimal.valueOf(11)
        String currencyCode = "foo"
        Price price = Price.builder()
                .value(value)
                .currencyCode(currencyCode)
                .build()

        when:
        CompletableFuture<PriceDto> futurePriceDto = priceService.getPrice(id)

        then:
        1 * mockPriceRepository.findById(id) >> Optional.of(price)
        0 * _

        and:
        PriceDto priceDto = futurePriceDto.get()
        priceDto.value == value
        priceDto.currencyCode == currencyCode
    }

    def 'savePrice - happy path'() {
        given:
        long id = 101
        BigDecimal value = BigDecimal.valueOf(11)
        String currencyCode = "foo"
        PriceDto priceDto = PriceDto.builder()
                .value(value)
                .currencyCode(currencyCode)
                .build()
        Price price

        when:
        priceService.savePrice(id, priceDto)

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
        String currencyCode = "foo"
        Price price = Price.builder()
                .value(value)
                .currencyCode(currencyCode)
                .build()

        when:
        PriceDto priceDto = priceService.transform(price)

        then:
        0 * _

        and:
        priceDto
        priceDto.value == value
        priceDto.currencyCode == currencyCode
    }

    def 'validatePrice - missing priceDto'() {
        when:
        priceService.validatePrice(null)

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
        priceService.validatePrice(priceDto)

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
        priceService.validatePrice(priceDto)

        then:
        0 * _

        and:
        thrown(ValidationException)
    }

    def 'validatePrice - happy path'() {
        given:
        PriceDto priceDto = PriceDto.builder()
                .value(BigDecimal.valueOf(11))
                .currencyCode("foo")
                .build()

        when:
        priceService.validatePrice(priceDto)

        then:
        0 * _

        and:
        noExceptionThrown()
    }
}
