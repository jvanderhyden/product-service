package com.myretail.product.service.impl;

import com.myretail.product.dto.ProductDto;
import com.myretail.product.service.PriceService;
import com.myretail.product.service.ProductService;
import com.myretail.product.service.RedSkyService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ProductServiceImpl.class);

    private final PriceService priceService;
    private final RedSkyService redSkyService;

    @Override
    public ProductDto getProduct(long id) {
        ProductDto.ProductDtoBuilder productDtoBuilder = ProductDto.builder().id(id);

        CompletableFuture<Void> price = priceService.getPrice(id)
                .thenAccept(productDtoBuilder::currentPrice)
                .exceptionally(e -> {
                    LOGGER.error("Failed to obtain price for {}", id, e);
                    return null;
                });

        CompletableFuture<Void> productName = redSkyService.getProductName(id)
                .thenAccept(productDtoBuilder::name)
                .exceptionally(e -> {
                    LOGGER.error("Failed to obtain product name for {}", id, e);
                    return null;
                });

        CompletableFuture.allOf(price, productName).join();
        return productDtoBuilder.build();
    }
}
