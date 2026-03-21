package com.learn.microservices.product_service.service;

import com.learn.microservices.product_service.entity.Product;
import com.learn.microservices.product_service.repository.ProductRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product saveProduct(Product product) {
        return productRepository.save(product);
    }

    public Product getProductById(Long id) {
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateQuantity(Long id, Integer quantity) {
        Product product = getProductById(id);
        product.setQuantity(quantity);
        return productRepository.save(product);
    }
}
