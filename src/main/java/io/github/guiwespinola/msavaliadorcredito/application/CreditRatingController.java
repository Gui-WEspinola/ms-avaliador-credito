package io.github.guiwespinola.msavaliadorcredito.application;

import io.github.guiwespinola.msavaliadorcredito.application.exception.CardRequestProtocolErrorException;
import io.github.guiwespinola.msavaliadorcredito.application.exception.CommunicationErrorException;
import io.github.guiwespinola.msavaliadorcredito.application.exception.CustomerDataNotFoundException;
import io.github.guiwespinola.msavaliadorcredito.domain.model.CardRequestData;
import io.github.guiwespinola.msavaliadorcredito.domain.model.CardRequestProtocol;
import io.github.guiwespinola.msavaliadorcredito.domain.model.CustomerRatingRequest;
import io.github.guiwespinola.msavaliadorcredito.domain.model.CustomerRatingResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("credit-rating")
@RequiredArgsConstructor
public class CreditRatingController {

    private final CreditRatingService ratingService;

    @GetMapping
    public String status() {
        return "ok";
    }

    @GetMapping(path = "/client-status")
    public ResponseEntity<?> getClientStatus(@RequestParam String cpf) {
        try {
            var status = ratingService.getClientStatus(cpf);
            return ResponseEntity.ok(status);
        } catch (CustomerDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (CommunicationErrorException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping
    public ResponseEntity rateCredit(@RequestBody CustomerRatingRequest customerData) {
        try {
            CustomerRatingResponse ratingResponse = ratingService.customerCreditRating(customerData.getCpf(), customerData.getIncome());
            return ResponseEntity.ok(ratingResponse);
        } catch (CustomerDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (CommunicationErrorException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }

    @PostMapping("request-cards")
    public ResponseEntity<?> requestCard(@RequestBody CardRequestData data) {
        try {
            CardRequestProtocol requestProtocol = ratingService.issueNewCard(data);
            return ResponseEntity.ok(requestProtocol);
        } catch (CardRequestProtocolErrorException e) {
            return ResponseEntity.internalServerError().body(e.getMessage());
        }
    }
}
