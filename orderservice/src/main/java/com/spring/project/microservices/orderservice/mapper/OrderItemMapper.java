package com.spring.project.microservices.orderservice.mapper;

import com.spring.project.microservices.orderservice.dto.OrderDto.OrderItemResponseDto;
import com.spring.project.microservices.orderservice.model.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class OrderItemMapper {

    public abstract OrderItemResponseDto toResponseDto(OrderItem entity);
    public abstract List<OrderItemResponseDto> toResponseDtoList(List<OrderItem> entities);
}
