package com.spring.project.microservices.userservice.payload;

import lombok.Data;

@Data
public class LoginRequest {
    private String username;
    private String password;
}
