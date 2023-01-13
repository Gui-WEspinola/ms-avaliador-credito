package io.github.guiwespinola.msavaliadorcredito.application;

import io.github.guiwespinola.msavaliadorcredito.clients.CardsResourceClient;
import io.github.guiwespinola.msavaliadorcredito.clients.CustomerResourceClient;
import io.github.guiwespinola.msavaliadorcredito.domain.model.CustomerCard;
import io.github.guiwespinola.msavaliadorcredito.domain.model.CustomerData;
import io.github.guiwespinola.msavaliadorcredito.domain.model.CustomerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditRatingService {

    private final CustomerResourceClient customerResourceClient;
    private final CardsResourceClient cardsResourceClient;
    public CustomerStatus getClientStatus(String cpf) {
        ResponseEntity<CustomerData> clientDataResponse = customerResourceClient.getCustomerData(cpf);
        ResponseEntity<List<CustomerCard>> cardsByClient = cardsResourceClient.getCardsByClient(cpf);

        return CustomerStatus
                .builder()
                .cards(cardsByClient.getBody())
                .customerData(clientDataResponse.getBody())
                .build();
    }
}
