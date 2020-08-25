package com.myretail.product.service.impl;

import com.myretail.product.dto.PriceDto;
import com.myretail.product.dto.ProductDto;
import com.myretail.product.model.Price;
import com.myretail.product.repository.PriceRepository;
import com.myretail.product.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final PriceRepository priceRepository;

    @Override
    public ProductDto find(long id) {
        PriceDto priceDto = priceRepository.findById(id)
                .map(this::transform)
                .orElseThrow(RuntimeException::new);
        return ProductDto.builder()
                .id(id)
                .currentPrice(priceDto)
                .build();
    }

    @Override
    public void save(long id, ProductDto productDto) {
        PriceDto priceDto = Optional.ofNullable(productDto)
                .map(ProductDto::getCurrentPrice)
                .orElseThrow(RuntimeException::new);
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
