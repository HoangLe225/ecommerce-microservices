package com.spring.project.microservices.orderservice.dto.OrderDto;

import com.spring.project.microservices.orderservice.model.OrderItem;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderResponseDto {
    private Long id;
    private String userName;
    private double totalPrice;
    private String status;
    private LocalDateTime createdAt;
    private List<OrderItemResponseDto> items;
}

