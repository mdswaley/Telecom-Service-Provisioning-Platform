package com.example.api_gateway.Filter;


import com.example.api_gateway.Service.JWTService;
import com.example.api_gateway.Service.RedisTokenService;
import lombok.RequiredArgsConstructor;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.List;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter implements GlobalFilter {

    private final JWTService jwtService;
    private final RedisTokenService redisTokenService;
    private static final List<String> PUBLIC_PATHS = List.of(
            "/auth",
            "/swagger-ui",
            "/v3/api-docs",
            "/customers/v3/api-docs",
            "/orders/v3/api-docs",
            "/provisioning/v3/api-docs",
            "/notifications/v3/api-docs"
    );

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        System.out.println("JWT FILTER EXECUTED");

        String path = exchange.getRequest()
                        .getURI()
                        .getPath();

        if (PUBLIC_PATHS.stream().anyMatch(path::startsWith)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest()
                        .getHeaders()
                        .getFirst(HttpHeaders.AUTHORIZATION);

//        System.out.println("Path = " + path);
//        System.out.println("Auth Header = " + authHeader);

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            System.out.println("JWT TOKEN MISSING");
            return writeErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "JWT Token is missing");
        }

        String token = authHeader.substring(7);
//        boolean valid = jwtService.isTokenValid(token);

//        System.out.println("Token = " + token);
//        System.out.println("Token Valid = " + valid);

        if (!jwtService.isTokenValid(token)) {
            return writeErrorResponse(exchange, HttpStatus.UNAUTHORIZED, "Invalid JWT Token");
        }


        Long userId = jwtService.getUserIdFromToken(token);
        String tokenJti = jwtService.getJti(token);


        String redisJti = redisTokenService.getCurrentJti(userId);

        if (redisJti == null || !tokenJti.equals(redisJti)) {
            return writeErrorResponse(
                    exchange,
                    HttpStatus.UNAUTHORIZED,
                    "Token has been replaced"
            );
        }

        return chain.filter(exchange);

    }

    private Mono<Void> writeErrorResponse(ServerWebExchange exchange, HttpStatus status, String message) {

        exchange.getResponse().setStatusCode(status);
        exchange.getResponse().getHeaders()
                .setContentType(MediaType.APPLICATION_JSON);

        String response = """
        {
          "status": %d,
          "message": "%s"
        }
        """.formatted(status.value(), message);

        return exchange.getResponse().writeWith(
                Mono.just(
                        exchange.getResponse()
                                .bufferFactory()
                                .wrap(response.getBytes())
                )
        );
    }
}
