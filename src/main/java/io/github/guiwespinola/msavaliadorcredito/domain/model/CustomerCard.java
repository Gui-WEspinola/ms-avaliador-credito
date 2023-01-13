package io.github.guiwespinola.msavaliadorcredito.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CustomerCard {

    private String name;
    private String cardFlag;
    private BigDecimal allowedCreditLimit;
}
