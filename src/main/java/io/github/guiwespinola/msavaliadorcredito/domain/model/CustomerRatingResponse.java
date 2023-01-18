package io.github.guiwespinola.msavaliadorcredito.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class CustomerRatingResponse {
    private List<ApprovedCard> approvedCards;
}
