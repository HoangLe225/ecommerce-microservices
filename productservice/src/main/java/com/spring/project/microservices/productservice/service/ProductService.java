package com.spring.project.microservices.productservice.service;

import com.spring.project.microservices.productservice.dto.ProductResponseDto;
import com.spring.project.microservices.productservice.exception.ResourceNotFoundException;
import com.spring.project.microservices.productservice.mapper.ProductMapper;
import com.spring.project.microservices.productservice.model.Product;
import com.spring.project.microservices.productservice.repository.ProductRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
    private final ProductRepository productRepository;
    private final ProductMapper productMapper;

    public ProductService(ProductRepository productRepository, ProductMapper productMapper) {
        this.productRepository = productRepository;
        this.productMapper = productMapper;
    }

    @Transactional(readOnly = true)
    public List<ProductResponseDto> findAllProducts() {
        List<Product> products = productRepository.findAll();
        return productMapper.toResponseDtoList(products);
    }

    @Transactional(readOnly = true)
    public ProductResponseDto findProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Product not found with ID: " + id));
        return productMapper.toResponseDto(product);
    }
}
