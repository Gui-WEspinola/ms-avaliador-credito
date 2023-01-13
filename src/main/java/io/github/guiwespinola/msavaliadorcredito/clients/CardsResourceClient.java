package io.github.guiwespinola.msavaliadorcredito.clients;

import io.github.guiwespinola.msavaliadorcredito.domain.model.CustomerCard;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "mscartoes", path = "/cards")
public interface CardsResourceClient {

    @GetMapping
    ResponseEntity<List<CustomerCard>> getCardsByClient(@RequestParam String cpf);
}
