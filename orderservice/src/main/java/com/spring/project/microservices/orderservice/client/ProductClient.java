package com.spring.project.microservices.orderservice.client;

import com.spring.project.microservices.orderservice.config.FeignClientConfig;
import com.spring.project.microservices.orderservice.dto.ProductResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "PRODUCT-SERVICE", configuration = FeignClientConfig.class)
public interface ProductClient {
    @GetMapping("/api/products/{id}")
    ProductResponseDto getProduct(@PathVariable("id") Long id);
}

