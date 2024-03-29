package com.myretail.product.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ProductDto {

    private final long id;
    private final String name;
    @JsonProperty("current_price")
    private final PriceDto currentPrice;

}
