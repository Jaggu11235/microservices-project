package com.learn.microservices.order_service.client;

import com.learn.microservices.order_service.dto.ProductResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

@FeignClient(name = "PRODUCT-SERVICE")
public interface ProductClient {

    @GetMapping("/products/{id}")
    ProductResponse getProductById(@PathVariable("id") Long id);

    @PutMapping("/products/{id}")
    void updateQuantity(@PathVariable("id")Long id, @RequestParam("quantity") Integer quantity);
}
