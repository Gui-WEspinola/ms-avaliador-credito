package io.github.guiwespinola.msavaliadorcredito.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Cards {

    private Long id;
    private String name;
    private String flag;
    private BigDecimal creditLimit;
}
