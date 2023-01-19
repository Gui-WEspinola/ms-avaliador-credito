package io.github.guiwespinola.msavaliadorcredito.domain.model;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CardRequestData {
    private Long cardId;
    private String cpf;
    private String address;
    private BigDecimal creditLimit;
}
