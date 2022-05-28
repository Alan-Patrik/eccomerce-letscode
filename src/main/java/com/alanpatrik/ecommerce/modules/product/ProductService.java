package com.alanpatrik.ecommerce.modules.product;

import com.alanpatrik.ecommerce.modules.clients.*;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final GetAllProductsClient getAllProductsClient;
    private final GetProductClientById getProductClientById;
    private final PostProductClient postProductClient;
    private final PutProductClient putProductClient;
    private final DeleteProductClient deleteProductClient;
    private final String URL_PRODUCT_CLIENT = "http://localhost:8081/api/v1/products";

    public Product verifyIfExistProductById(Long id) {
        Product receivedProduct = getProductClientById.execute(URL_PRODUCT_CLIENT + id);
        if (receivedProduct.equals(null)) {
            throw new IllegalArgumentException(
                    String.format("Produto com o id %s, não foi encontrado", id));
        }

        return receivedProduct;
    }

    @Cacheable(value = "productList")
    public List<Product> getAll() {
        List<Product> productList = getAllProductsClient.execute(URL_PRODUCT_CLIENT);
        return productList;
    }

    public Product create(Product product) {
        Product receivedProduct = postProductClient.execute(URL_PRODUCT_CLIENT, product);
        return receivedProduct;
    }

    public Product update(Long id, Product product) {
        Product receivedProduct = verifyIfExistProductById(id);
        receivedProduct.setId(product.getId());
        receivedProduct.setName(product.getName());
        receivedProduct.setPrice(product.getPrice());
        receivedProduct.setQuantity(product.getQuantity());

        receivedProduct = putProductClient.execute(URL_PRODUCT_CLIENT + receivedProduct.getId(), product);
        return receivedProduct;
    }

    public void delete(Long id) {
        Product receivedProduct = verifyIfExistProductById(id);
        deleteProductClient.execute(URL_PRODUCT_CLIENT + receivedProduct.getId());
    }
}
