package com.alanpatrik.ecommerce.modules.clients;

import com.alanpatrik.ecommerce.modules.product.Product;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class PostProductClient {

    public Product execute(String url, Product product) {
        RestTemplate restTemplate = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);
        HttpEntity<?> entity = new HttpEntity<>(product);
        ResponseEntity<Product> infoTransactionResponseEntity = restTemplate.exchange(
                url,
                HttpMethod.POST,
                entity,
                Product.class);

        if (infoTransactionResponseEntity.getStatusCode().isError()) {
            throw new RuntimeException("Erro ao conectar serviço de transação.");
        }

        return infoTransactionResponseEntity.getBody();
    }
}
