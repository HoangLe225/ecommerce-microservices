//package com.spring.project.microservices.apigateway.security;
//
//import io.jsonwebtoken.Claims;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpHeaders;
//import org.springframework.http.HttpStatus;
//import org.springframework.stereotype.Component;
//import org.springframework.web.server.ServerWebExchange;
//import org.springframework.cloud.gateway.filter.GatewayFilterChain;
//import org.springframework.cloud.gateway.filter.GlobalFilter;
//import reactor.core.publisher.Mono;
//
//import java.util.List;
//
//@Component
//public class JwtAuthFilter implements GlobalFilter {
//
//    private final JwtUtil jwtUtil;
//    // endpoints that don't require auth
//    private final List<String> openEndpoints = List.of("/api/auth/login", "/api/auth/register");
//
//    @Autowired
//    public JwtAuthFilter(JwtUtil jwtUtil) {
//        this.jwtUtil = jwtUtil;
//    }
//
//    @Override
//    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
//        String path = exchange.getRequest().getURI().getPath();
//        // allow open endpoints
//        for (String p : openEndpoints) {
//            if (path.startsWith(p)) return chain.filter(exchange);
//        }
//
//        String auth = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
//        if (auth == null || !auth.startsWith("Bearer ")) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//        String token = auth.substring(7);
//        if (!jwtUtil.validateToken(token)) {
//            exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
//            return exchange.getResponse().setComplete();
//        }
//        Claims claims = jwtUtil.getClaims(token);
//        String username = claims.getSubject();
//        Object roles = claims.get("roles"); // can be list or string
//        String rolesStr = roles instanceof List ? String.join(",", (List<String>) roles) : String.valueOf(roles);
//
//        // forward headers to downstream
//        exchange.getRequest().mutate()
//                .header("X-User-Name", username)
//                .header("X-User-Roles", rolesStr)
//                .build();
//
//        return chain.filter(exchange);
//    }
//}
