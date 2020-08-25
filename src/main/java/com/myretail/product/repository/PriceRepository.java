package com.myretail.product.repository;

import com.myretail.product.model.Price;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface PriceRepository extends MongoRepository<Price,Long> {
}
