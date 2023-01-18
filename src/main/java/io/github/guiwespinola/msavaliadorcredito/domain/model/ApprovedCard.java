package io.github.guiwespinola.msavaliadorcredito.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class ApprovedCard {
    private String card;
    private String cardFlag;
    private BigDecimal approvedLimit;
}
