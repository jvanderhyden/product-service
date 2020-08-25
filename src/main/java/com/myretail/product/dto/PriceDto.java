package com.myretail.product.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class PriceDto {

    private final BigDecimal value;
    @JsonProperty("currency_code")
    private final String currencyCode;

}
