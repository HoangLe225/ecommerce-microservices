package com.spring.project.microservices.orderservice.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.ZonedDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ErrorResponse {
    private ZonedDateTime timestamp;
    private int status;
    private String error;
    private String message;
    private String path;
    private String errorCode;
    private List<FieldErrorDetail> details;

    // Getters, Setters, Constructors

    public ErrorResponse(ZonedDateTime timestamp, int status, String error, String message,
                         String path, String errorCode, List<FieldErrorDetail> details) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errorCode = errorCode;
        this.details = details;
    }

    // getters/setters omitted for brevity
}
