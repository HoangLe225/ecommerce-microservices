package com.spring.project.microservices.orderservice.handler;


import com.spring.project.microservices.orderservice.exception.ErrorResponse;

public class CustomFeignException extends RuntimeException {
    private final int status;
    private final ErrorResponse errorResponse;

    public CustomFeignException(int status, String message, ErrorResponse errorResponse) {
        super(message);
        this.status = status;
        this.errorResponse = errorResponse;
    }

    public int getStatus() { return status; }
    public ErrorResponse getErrorResponse() { return errorResponse; }
}

