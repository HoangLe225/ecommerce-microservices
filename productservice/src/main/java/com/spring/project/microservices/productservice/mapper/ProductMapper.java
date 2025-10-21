package com.spring.project.microservices.productservice.mapper;

import com.spring.project.microservices.productservice.dto.ProductResponseDto;
import com.spring.project.microservices.productservice.model.Product;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ProductMapper {
    public abstract ProductResponseDto toResponseDto(Product entity);
    public abstract List<ProductResponseDto> toResponseDtoList(List<Product> entities);
}
