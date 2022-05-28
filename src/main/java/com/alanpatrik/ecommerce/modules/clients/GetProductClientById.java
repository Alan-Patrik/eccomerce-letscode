package com.alanpatrik.ecommerce.modules.clients;

import com.alanpatrik.ecommerce.modules.product.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class GetProductClientById {

    public Product execute(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<Product> transactionResponseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                Product.class);

        if (transactionResponseEntity.getStatusCode().isError()) {
            throw new RuntimeException("Erro ao retornar Lista de transações.");
        }

        return transactionResponseEntity.getBody();
    }
}
