package com.myretail.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class RedSkyDto {

    private final Product product;

    @Getter
    @AllArgsConstructor
    public static class Product {
        private final Item item;
    }

    @Getter
    @AllArgsConstructor
    public static class Item {
        @JsonProperty("product_description")
        private final ProductDescription productDescription;
    }

    @Getter
    @AllArgsConstructor
    public static class ProductDescription {
        private final String title;
    }
}
