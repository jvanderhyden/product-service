package com.myretail.product.service.impl;

import com.myretail.product.dto.PriceDto;
import com.myretail.product.dto.ProductDto;
import com.myretail.product.exception.EntityNotFoundException;
import com.myretail.product.exception.ValidationException;
import com.myretail.product.model.Price;
import com.myretail.product.repository.PriceRepository;
import com.myretail.product.service.ProductService;
import com.myretail.product.service.RedSkyService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class ProductServiceImpl implements ProductService {

    private final PriceRepository priceRepository;
    private final RedSkyService redSkyService;

    @Override
    public ProductDto find(long id) {
        PriceDto priceDto = priceRepository.findById(id)
                .map(this::transform)
                .orElseThrow(() -> new EntityNotFoundException("Price not found"));
        String productName = redSkyService.getProductName(id)
                .orElseThrow(() -> new EntityNotFoundException("Product name not found"));
        return ProductDto.builder()
                .id(id)
                .name(productName)
                .currentPrice(priceDto)
                .build();
    }

    @Override
    public void save(long id, PriceDto priceDto) {
        validatePrice(priceDto);
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

    private void validatePrice(PriceDto priceDto) {
        if (priceDto == null) {
            throw new ValidationException("Request must not be empty");
        }
        if (priceDto.getValue() == null) {
            throw new ValidationException("Missing required field: value");
        }
        if (priceDto.getCurrencyCode() == null) {
            throw new ValidationException("Missing required field: currency_code");
        }
    }
}
