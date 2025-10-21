package com.spring.project.microservices.orderservice.dto.OrderDto;

import jakarta.persistence.Column;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
public class OrderItemResponseDto {
    private Long id;
    private String productName;
    private int quantity;
    private double price;
    private Integer subtotal;
}
