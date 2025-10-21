package com.spring.project.microservices.orderservice.client;

import com.spring.project.microservices.orderservice.config.FeignClientConfig;
import com.spring.project.microservices.orderservice.dto.UserResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "USER-SERVICE", configuration = FeignClientConfig.class)
public interface UserClient {
    @GetMapping("/api/users/{id}")
    ResponseEntity<UserResponseDto> getUser(@PathVariable("id") Long id);
}

