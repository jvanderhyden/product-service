package com.myretail.product.service;

import java.util.Optional;

public interface RedSkyService {

    Optional<String> getProductName(long id);

}
