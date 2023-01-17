package io.github.guiwespinola.msavaliadorcredito.application;

import io.github.guiwespinola.msavaliadorcredito.application.exception.ClientDataNotFoundException;
import io.github.guiwespinola.msavaliadorcredito.application.exception.CommunicationErrorException;
import io.github.guiwespinola.msavaliadorcredito.domain.model.CustomerStatus;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
        } catch (ClientDataNotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (CommunicationErrorException e) {
            return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
        }
    }
}
