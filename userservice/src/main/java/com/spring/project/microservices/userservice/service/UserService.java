package com.spring.project.microservices.userservice.service;

import com.spring.project.microservices.userservice.dto.UserResponseDto;
import com.spring.project.microservices.userservice.exception.ResourceNotFoundException;
import com.spring.project.microservices.userservice.mapper.UserMapper;
import com.spring.project.microservices.userservice.model.User;
import com.spring.project.microservices.userservice.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;
    private final UserMapper userMapper;

    public UserService(UserRepository userRepository, UserMapper userMapper) {
        this.userRepository = userRepository;
        this.userMapper = userMapper;
    }

    @Transactional(readOnly = true)
    public List<UserResponseDto> findAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toResponseDtoList(users);
    }

    @Transactional(readOnly = true)
    public UserResponseDto findUserById(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        return userMapper.toResponseDto(user);
    }
}
