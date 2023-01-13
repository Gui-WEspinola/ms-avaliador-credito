package io.github.guiwespinola.msavaliadorcredito.application;

import io.github.guiwespinola.msavaliadorcredito.domain.model.CustomerStatus;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<CustomerStatus> getClientStatus(@RequestParam String cpf) {
        CustomerStatus status = ratingService.getClientStatus(cpf);
        return ResponseEntity.ok(status);
    }
}
