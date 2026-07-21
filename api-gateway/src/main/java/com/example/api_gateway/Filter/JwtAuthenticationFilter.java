package com.example.api_gateway.Filter;


import com.example.api_gateway.Advice.ApiError;
import com.example.api_gateway.Service.JWTService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JWTService jwtService;
    private final ObjectMapper objectMapper;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        String path = exchange.getRequest()
                        .getURI()
                        .getPath();

        if (path.startsWith("/auth")) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

//        System.out.println("Path = " + path);
//        System.out.println("Auth Header = " + authHeader);


        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            return writeErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "JWT Token is missing");
        }

        String token = authHeader.substring(7);
//        boolean valid = jwtService.isTokenValid(token);

//        System.out.println("Token = " + token);
//        System.out.println("Token Valid = " + valid);

        if (!jwtService.isTokenValid(token)) {
            return writeErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Invalid JWT Token");
        }

        return chain.filter(exchange);
    }

    private Mono<Void> writeErrorResponse(ServerWebExchange exchange, HttpStatus status, String message) {

        ApiError error = ApiError.builder()
                .statusCode(status)
                .error(message)
                .timeStamp(LocalDateTime.now())
                .build();

        byte[] bytes;

        try {
            bytes = new ObjectMapper()
                    .writeValueAsBytes(error);
        } catch (Exception e) {
            return exchange.getResponse().setComplete();
        }

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders()
                .add(HttpHeaders.CONTENT_TYPE,
                        MediaType.APPLICATION_JSON_VALUE);

        return exchange.getResponse()
                .writeWith(
                        Mono.just(
                                exchange.getResponse()
                                        .bufferFactory()
                                        .wrap(bytes)
                        )
                );
    }
}
