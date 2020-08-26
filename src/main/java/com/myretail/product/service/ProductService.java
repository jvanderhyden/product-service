package com.myretail.product.service;

import com.myretail.product.dto.PriceDto;
import com.myretail.product.dto.ProductDto;

public interface ProductService {

    ProductDto find(long id);

    void save(long id, PriceDto priceDto);

}
