package com.myretail.product.model;

import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
@Builder
public class Price {

    private final long id;
    private final BigDecimal value;
    private final String currencyCode;

}
