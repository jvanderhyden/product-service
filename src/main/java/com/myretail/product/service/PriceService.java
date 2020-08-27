package com.myretail.product.service;

import com.myretail.product.dto.PriceDto;

import java.util.concurrent.CompletableFuture;

public interface PriceService {

    CompletableFuture<PriceDto> getPrice(long id);

    void savePrice(long id, PriceDto priceDto);

}
