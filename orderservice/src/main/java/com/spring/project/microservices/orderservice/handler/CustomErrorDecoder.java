package com.spring.project.microservices.orderservice.handler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.spring.project.microservices.orderservice.exception.ErrorResponse;
import com.spring.project.microservices.orderservice.exception.ResourceNotFoundException;
import feign.Response;
import feign.codec.ErrorDecoder;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class CustomErrorDecoder implements ErrorDecoder {

    private final ObjectMapper objectMapper;

    public CustomErrorDecoder() {
        this.objectMapper = new ObjectMapper();
        this.objectMapper.registerModule(new JavaTimeModule()); // giúp parse ZonedDateTime
    }

    @Override
    public Exception decode(String methodKey, Response response) {
        try {
            String body = response.body() != null
                    ? new String(response.body().asInputStream().readAllBytes(), StandardCharsets.UTF_8)
                    : "";

            if (body.isEmpty()) {
                return new RuntimeException("Empty error body from ProductService (status " + response.status() + ")");
            }

            try {
                // Parse JSON ErrorResponse
                ErrorResponse error = objectMapper.readValue(body, ErrorResponse.class);

                switch (response.status()) {
                    case 404:
                        return new ResourceNotFoundException(error.getMessage());
                    case 400:
                        return new IllegalArgumentException(error.getMessage());
                    default:
                        return new RuntimeException("ProductService error: " + error.getMessage());
                }
            } catch (Exception e) {
                return new RuntimeException("Cannot parse ProductService error JSON: " + body, e);
            }

        } catch (IOException e) {
            return new RuntimeException("Cannot parse ProductService error response", e);
        }
    }
}
