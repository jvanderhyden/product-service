package com.myretail.product.service.impl;

import com.myretail.product.dto.PriceDto;
import com.myretail.product.model.Price;
import com.myretail.product.repository.PriceRepository;
import com.myretail.product.service.PriceService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class PriceServiceImpl implements PriceService {

    private final PriceRepository priceRepository;

    @Async
    @Override
    public CompletableFuture<PriceDto> getPrice(long id) {
        PriceDto priceDto = priceRepository.findById(id)
                .map(this::transform)
                .orElse(null);
        return CompletableFuture.completedFuture(priceDto);
    }

    @Override
    public void savePrice(long id, PriceDto priceDto) {
        Price price = Price.builder()
                .id(id)
                .currencyCode(priceDto.getCurrencyCode())
                .value(priceDto.getValue())
                .build();
        priceRepository.save(price);
    }

    private PriceDto transform(Price price) {
        return PriceDto.builder()
                .value(price.getValue())
                .currencyCode(price.getCurrencyCode())
                .build();
    }
}
