package com.learn.microservices.product_service.controller;

import com.learn.microservices.product_service.entity.Product;
import com.learn.microservices.product_service.service.ProductService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @GetMapping("/{id}")
    public Product getProduct(@PathVariable Long id) {
        return productService.getProductById(id);
    }

    @GetMapping
    public List<Product> getAllProducts() {
        return productService.getAllProducts();
    }

    @PutMapping
    public Product updateProduct(@RequestBody Product product) {
        return productService.saveProduct(product);
    }

    @PatchMapping("/{id}")
    public Product updateQuantity(@PathVariable Long id, @RequestParam Integer quantity) {
        return productService.updateQuantity(id, quantity);
    }
}