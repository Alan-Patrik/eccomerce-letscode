package com.alanpatrik.ecommerce.modules.clients;

import com.alanpatrik.ecommerce.modules.product.Product;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class GetAllProductsClient {

    public List<Product> execute(String url) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(headers);
        ResponseEntity<List<Product>> transactionListResponseEntity = restTemplate.exchange(
                url,
                HttpMethod.GET,
                entity,
                new ParameterizedTypeReference<List<Product>>() {
                });

        List<Product> transactionDTOList = transactionListResponseEntity.getBody();

        if (transactionListResponseEntity.getStatusCode().isError()) {
            throw new RuntimeException("Erro ao retornar Lista de transações.");
        }

        return transactionDTOList;
    }
}
