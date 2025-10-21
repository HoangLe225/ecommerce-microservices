package com.spring.project.microservices.orderservice.controller;

import com.spring.project.microservices.orderservice.client.ProductClient;
import com.spring.project.microservices.orderservice.client.UserClient;
import com.spring.project.microservices.orderservice.dto.OrderDto.OrderItemRequestDto;
import com.spring.project.microservices.orderservice.dto.OrderDto.OrderItemResponseDto;
import com.spring.project.microservices.orderservice.dto.OrderDto.OrderRequestDto;
import com.spring.project.microservices.orderservice.dto.OrderDto.OrderResponseDto;
import com.spring.project.microservices.orderservice.model.Order;
import com.spring.project.microservices.orderservice.model.OrderItem;
import com.spring.project.microservices.orderservice.repository.OrderRepository;
import com.spring.project.microservices.orderservice.service.OrderItemService;
import com.spring.project.microservices.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/orders")
public class OrderController {

    private final OrderRepository orderRepository;
    private final UserClient userClient;
    private final ProductClient productClient;
    private final OrderService orderService;
    private final OrderItemService orderItemService;

    public OrderController(OrderRepository orderRepository, UserClient userClient, ProductClient productClient, OrderService orderService, OrderItemService orderItemService) {
        this.orderRepository = orderRepository;
        this.userClient = userClient;
        this.productClient = productClient;
        this.orderService = orderService;
        this.orderItemService = orderItemService;
    }

    @PostMapping
    public ResponseEntity<?> createOrder(@RequestBody OrderRequestDto request) {
        return orderService.saveOrder(request);
    }

//    @GetMapping("/{id}")
//    public ResponseEntity<?> getOrder(@PathVariable Long id) {
//        return orderService.getOrderWithProduct(id);
//    }

    @GetMapping("/{id}")
    public OrderResponseDto getOrderById(@PathVariable Long id) {
        return orderService.getOrderById(id);
    }

    @GetMapping("/{id}/order_items")
    public List<OrderItemResponseDto> getOrderItembyOrder(@PathVariable Long id) {
        return orderItemService.getOrderItemByOrderId(id);
    }
}


