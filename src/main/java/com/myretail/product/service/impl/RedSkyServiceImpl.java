package com.myretail.product.service.impl;

import com.myretail.product.dto.RedSkyDto;
import com.myretail.product.service.RedSkyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Optional;

@AllArgsConstructor
@Service
public class RedSkyServiceImpl implements RedSkyService {

    private static final String PATH = "/v3/pdp/tcin/{id}";
    private final RestTemplate restTemplate;

    @Override
    public Optional<String> getProductName(long id) {
        RedSkyDto redSkyDto = restTemplate.getForObject(PATH, RedSkyDto.class, id);
        return Optional.ofNullable(redSkyDto)
                .map(RedSkyDto::getProduct)
                .map(RedSkyDto.Product::getItem)
                .map(RedSkyDto.Item::getProductDescription)
                .map(RedSkyDto.ProductDescription::getTitle);
    }
}
