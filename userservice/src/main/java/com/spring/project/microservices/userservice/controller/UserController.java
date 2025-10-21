package com.spring.project.microservices.userservice.controller;

import com.spring.project.microservices.userservice.dto.UserResponseDto;
import com.spring.project.microservices.userservice.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<UserResponseDto> getAllProducts() {
        return userService.findAllUsers();
    }

    @GetMapping("/{id}")
    public UserResponseDto getProductById(@PathVariable Long id) {
        return userService.findUserById(id);
    }
}
