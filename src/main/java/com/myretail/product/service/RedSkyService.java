package com.myretail.product.service;

import java.util.concurrent.CompletableFuture;

public interface RedSkyService {

    CompletableFuture<String> getProductName(long id);

}
