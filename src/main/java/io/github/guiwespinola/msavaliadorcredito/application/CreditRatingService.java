package io.github.guiwespinola.msavaliadorcredito.application;

import feign.FeignException;
import io.github.guiwespinola.msavaliadorcredito.application.exception.CommunicationErrorException;
import io.github.guiwespinola.msavaliadorcredito.application.exception.CustomerDataNotFoundException;
import io.github.guiwespinola.msavaliadorcredito.clients.CardsResourceClient;
import io.github.guiwespinola.msavaliadorcredito.clients.CustomerResourceClient;
import io.github.guiwespinola.msavaliadorcredito.domain.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CreditRatingService {

    private final CustomerResourceClient customerResourceClient;
    private final CardsResourceClient cardsResourceClient;

    public CustomerStatus getClientStatus(String cpf)
            throws CustomerDataNotFoundException, CommunicationErrorException {
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
                throw new CustomerDataNotFoundException();
            }
            throw new CommunicationErrorException(e.getMessage(), e.status());
        }
    }

    public CustomerRatingResponse customerCreditRating(String cpf, Long income)
            throws CustomerDataNotFoundException, CommunicationErrorException {
        try {
            ResponseEntity<CustomerData> customerDataResponse = customerResourceClient.getCustomerData(cpf);
            ResponseEntity<List<Cards>> cardsResponse = cardsResourceClient.getCardIncomeLessThan(income);

            List<Cards> cardsList = cardsResponse.getBody();
            var approvedCardList = cardsList.stream().map(cards -> {

                CustomerData customerData = customerDataResponse.getBody();

                BigDecimal basicLimit = cards.getCreditLimit();
                BigDecimal ageBD = BigDecimal.valueOf(customerData.getAge());
                var fator = ageBD.divide(BigDecimal.valueOf(10));
                BigDecimal approvedLimit = fator.multiply(basicLimit);

                ApprovedCard approvedCard = new ApprovedCard();
                approvedCard.setCard(cards.getName());
                approvedCard.setCardFlag(cards.getFlag());
                approvedCard.setApprovedLimit(approvedLimit);

                return approvedCard;
            }).toList();

            return new CustomerRatingResponse(approvedCardList);

        } catch (FeignException.FeignClientException e) {
            if (HttpStatus.NOT_FOUND.value() == e.status()) {
                throw new CustomerDataNotFoundException();
            }
            throw new CommunicationErrorException(e.getMessage(), e.status());
        }
    }
}
