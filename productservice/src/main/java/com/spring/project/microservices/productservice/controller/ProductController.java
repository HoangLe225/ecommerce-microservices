package com.spring.project.microservices.productservice.controller;

import com.spring.project.microservices.productservice.dto.ProductResponseDto;
import com.spring.project.microservices.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @Value("${custom.message:default-msg}")
    private String message;

    @GetMapping("/msg")
    public String msg() {
        return "Message from config: " + message;
    }

    @GetMapping
    public List<ProductResponseDto> getAllProducts() {
        return productService.findAllProducts();
    }

    @GetMapping("/{id}")
    public ProductResponseDto getProductById(@PathVariable Long id) {
        return productService.findProductById(id);
    }
}
