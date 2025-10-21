package com.spring.project.microservices.orderservice.service;

import com.spring.project.microservices.orderservice.client.ProductClient;
import com.spring.project.microservices.orderservice.dto.OrderDto.OrderItemResponseDto;
import com.spring.project.microservices.orderservice.dto.ProductResponseDto;
import com.spring.project.microservices.orderservice.exception.ResourceNotFoundException;
import com.spring.project.microservices.orderservice.mapper.OrderItemMapper;
import com.spring.project.microservices.orderservice.model.OrderItem;
import com.spring.project.microservices.orderservice.repository.OrderItemRepository;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.retry.annotation.Retry;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class OrderItemService {
    private final OrderItemRepository orderItemRepository;
    private final OrderItemMapper orderItemMapper;
    private final ProductClient productClient;

    public OrderItemService(OrderItemRepository orderItemRepository, OrderItemMapper orderItemMapper, ProductClient productClient) {
        this.orderItemRepository = orderItemRepository;
        this.orderItemMapper = orderItemMapper;
        this.productClient = productClient;
    }

    public OrderItem getOrderItemById(Long id) {
        return orderItemRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Order_Items not found with ID: " + id));
    }

    @CircuitBreaker(name = "productService", fallbackMethod = "fallbackGetProduct")
    @Retry(name = "productService")
    public List<OrderItemResponseDto> getOrderItemByOrderId(Long orderId) {
        List<OrderItem> orderItems = orderItemRepository.findByOrderId(orderId);
        List<OrderItemResponseDto> orderItemResponseDtos = new ArrayList<>();
        orderItems.forEach(item -> {
            ProductResponseDto productResponseDto = productClient.getProduct(item.getProductId());
            OrderItemResponseDto orderItemResponseDto = orderItemMapper.toResponseDto(item);
            orderItemResponseDto.setProductName(productResponseDto.getName());
            orderItemResponseDtos.add(orderItemResponseDto);
        });

        return orderItemResponseDtos;
    }

    // fallback được gọi khi circuit breaker "mở"
    public List<OrderItemResponseDto> fallbackGetProduct(Long orderId, Throwable throwable) {
        String message = (throwable.getCause() != null)
                ? throwable.getCause().getMessage()
                : throwable.getMessage();
        throw new RuntimeException("Product Service currently unavailable: " + message);
    }
}
