package io.github.guiwespinola.msavaliadorcredito.domain.model;

import lombok.Data;

@Data
public class CustomerRatingRequest {

    private String cpf;
    private Long income;
}
