package com.spring.project.microservices.orderservice.config;

import com.spring.project.microservices.orderservice.handler.CustomErrorDecoder;
import feign.RequestInterceptor;
import feign.RequestTemplate;
import feign.codec.ErrorDecoder;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;

@Configuration
public class FeignClientConfig {
    @Bean
    public ErrorDecoder errorDecoder() {
        return new CustomErrorDecoder();
    }

    /**
     * Feign client configuration used by specific @FeignClient definitions.
     * Interceptor takes the Authorization header from the incoming HTTP request
     * (OrderService request) and copies it to outgoing Feign requests.
     */
    @Bean
    public RequestInterceptor requestInterceptor() {
        return new RequestInterceptor() {
            @Override
            public void apply(RequestTemplate template) {
                RequestAttributes attrs = RequestContextHolder.getRequestAttributes();
                if (attrs == null) {
                    return;
                }
                Object reqObj = attrs.resolveReference(RequestAttributes.REFERENCE_REQUEST);
                if (reqObj instanceof HttpServletRequest req) {
                    String auth = req.getHeader("Authorization");
                    if (auth != null && !auth.isBlank()) {
                        template.header("Authorization", auth);
                    }
                }
            }
        };
    }
}
