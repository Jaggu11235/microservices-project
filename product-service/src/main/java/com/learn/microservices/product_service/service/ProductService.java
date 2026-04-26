package com.learn.microservices.product_service.service;

import com.learn.microservices.product_service.entity.Product;
import com.learn.microservices.product_service.repository.ProductRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private static final Logger logger =  LoggerFactory.getLogger(ProductService.class);

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
        logger.info("Get product by Id: {}", id);
        return productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));
    }

    public Product updateQuantity(Long id, Integer quantity) {
        logger.info("Update product by Id: {} with the quantity: {}", id, quantity);
        Product product = getProductById(id);
        product.setQuantity(quantity);
        return productRepository.save(product);
    }
}
