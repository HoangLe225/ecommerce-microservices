package com.spring.project.microservices.orderservice.dto.OrderDto;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class OrderItemRequestDto {
    private Long productId;
    private int quantity;
}
