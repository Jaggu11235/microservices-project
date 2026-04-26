package com.learn.microservices.order_service.service;

import com.learn.microservices.order_service.client.ProductClient;
import com.learn.microservices.order_service.client.UserClient;
import com.learn.microservices.order_service.dto.ProductResponse;
import com.learn.microservices.order_service.dto.UserResponse;
import com.learn.microservices.order_service.entity.Order;
import com.learn.microservices.order_service.repository.OrderRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final ProductClient productClient;
    private static final Logger logger = LoggerFactory.getLogger(OrderService.class);

    public OrderService(OrderRepository orderRepository, UserClient userClient, ProductClient productClient) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.productClient = productClient;
    }

    public Order placeOrder(Long userId, Long productId, Integer quantity) {
        logger.info("Order creation started");
        UserResponse user = userClient.getUserById(userId);
        if(user == null) {
            throw new RuntimeException("User Not found");
        }

        ProductResponse productResponse = productClient.getProductById(productId);
        if(productResponse == null){
            throw new RuntimeException("Product not found");
        }

        if(productResponse.getQuantity() < quantity) {
            throw new RuntimeException("Insufficient quantity");
        }

        productClient.updateQuantity(productResponse.getId(), productResponse.getQuantity()-quantity);

        Order order = new Order();
        order.setUserId(userId);
        order.setProductId(productId);
        order.setQuantity(quantity);
        order.setStatus("PLACED");

        return orderRepository.save(order);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() ->new RuntimeException("Order not found"));
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
}
