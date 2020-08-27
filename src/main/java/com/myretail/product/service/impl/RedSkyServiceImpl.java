package com.myretail.product.service.impl;

import com.myretail.product.dto.RedSkyDto;
import com.myretail.product.service.RedSkyService;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;
import java.util.concurrent.CompletableFuture;

@AllArgsConstructor
@Service
public class RedSkyServiceImpl implements RedSkyService {

    private final RestTemplate restTemplate;

    @Async
    @Override
    public CompletableFuture<String> getProductName(long id) {
        RedSkyDto redSkyDto = restTemplate.getForObject("/v3/pdp/tcin/{id}", RedSkyDto.class, id);
        String productName = Optional.ofNullable(redSkyDto)
                .map(RedSkyDto::getProduct)
                .map(RedSkyDto.Product::getItem)
                .map(RedSkyDto.Item::getProductDescription)
                .map(RedSkyDto.ProductDescription::getTitle)
                .orElse(null);

        return CompletableFuture.completedFuture(productName);
    }
}
