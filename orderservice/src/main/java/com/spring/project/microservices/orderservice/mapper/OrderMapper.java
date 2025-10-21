package com.spring.project.microservices.orderservice.mapper;

import com.spring.project.microservices.orderservice.dto.OrderDto.OrderItemResponseDto;
import com.spring.project.microservices.orderservice.dto.OrderDto.OrderResponseDto;
import com.spring.project.microservices.orderservice.model.Order;
import com.spring.project.microservices.orderservice.model.OrderItem;
import org.mapstruct.Mapper;

import java.util.List;
@Mapper(componentModel = "spring")
public abstract class OrderMapper {
    public abstract OrderResponseDto toResponseDto(Order entity);
    public abstract List<OrderResponseDto> toResponseDtoList(List<Order> entities);
}
