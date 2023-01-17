package io.github.guiwespinola.msavaliadorcredito.application;

import feign.FeignException;
import io.github.guiwespinola.msavaliadorcredito.application.exception.ClientDataNotFoundException;
import io.github.guiwespinola.msavaliadorcredito.application.exception.CommunicationErrorException;
import io.github.guiwespinola.msavaliadorcredito.clients.CardsResourceClient;
import io.github.guiwespinola.msavaliadorcredito.clients.CustomerResourceClient;
import io.github.guiwespinola.msavaliadorcredito.domain.model.CustomerCard;
import io.github.guiwespinola.msavaliadorcredito.domain.model.CustomerData;
import io.github.guiwespinola.msavaliadorcredito.domain.model.CustomerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditRatingService {

    private final CustomerResourceClient customerResourceClient;
    private final CardsResourceClient cardsResourceClient;
    public CustomerStatus getClientStatus(String cpf)
            throws ClientDataNotFoundException, CommunicationErrorException {
        try {

            ResponseEntity<CustomerData> clientDataResponse = customerResourceClient.getCustomerData(cpf);
            ResponseEntity<List<CustomerCard>> cardsByClient = cardsResourceClient.getCardsByClient(cpf);

            return CustomerStatus
                    .builder()
                    .cards(cardsByClient.getBody())
                    .customerData(clientDataResponse.getBody())
                    .build();
        } catch (FeignException.FeignClientException e) {
            if (HttpStatus.NOT_FOUND.value() == e.status()) {
                throw new ClientDataNotFoundException();
            }
            throw new CommunicationErrorException(e.getMessage(), e.status());
        }
    }
}
