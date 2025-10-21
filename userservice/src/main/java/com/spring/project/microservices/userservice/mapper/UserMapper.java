package com.spring.project.microservices.userservice.mapper;

import com.spring.project.microservices.userservice.dto.UserResponseDto;
import com.spring.project.microservices.userservice.model.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {
    public abstract UserResponseDto toResponseDto(User entity);
    public abstract List<UserResponseDto> toResponseDtoList(List<User> entities);
}
