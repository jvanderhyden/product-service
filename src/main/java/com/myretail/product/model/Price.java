package com.myretail.product.model;

import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;

import java.math.BigDecimal;

@Getter
@Builder
public class Price {

    @Id
    private final long id;
    private final BigDecimal value;
    private final String currencyCode;

}
