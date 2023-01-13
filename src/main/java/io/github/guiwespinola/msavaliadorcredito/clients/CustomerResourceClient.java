package io.github.guiwespinola.msavaliadorcredito.clients;

import io.github.guiwespinola.msavaliadorcredito.domain.model.CustomerData;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "msclientes", path = "/clients") // caso seja passada uma URL em value, nao será feito o load ballancing
public interface CustomerResourceClient {

    @GetMapping(params = "cpf")
    ResponseEntity<CustomerData> getCustomerData(@RequestParam("cpf") String cpf);
}

//@FeignClient(url = "http://localhost:8080", path = "/clients")
// Essa seria a anotação em caso de utilização de um cliente http direto (caso do viacep)