package com.spring.project.microservices.productservice.dto;

import com.spring.project.microservices.productservice.model.Category;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ProductResponseDto {
    private Long id;
    private String name;
    private double price;
    private int stock;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
