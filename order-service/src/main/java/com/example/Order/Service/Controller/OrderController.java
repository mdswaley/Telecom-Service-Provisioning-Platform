package com.example.Order.Service.Controller;

import com.example.Order.Service.OrderDTO.OrderRequest;
import com.example.Order.Service.OrderDTO.OrderResponse;
import com.example.Order.Service.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<OrderResponse> createOrder(
            @RequestBody OrderRequest request) {

        return ResponseEntity.ok(
                orderService.createOrder(request)
        );
    }

    @GetMapping("/{orderNumber}")
    public ResponseEntity<OrderResponse> getOrder(
            @PathVariable String orderNumber) {

        return ResponseEntity.ok(
                orderService.getOrder(orderNumber)
        );
    }

    @GetMapping
    public ResponseEntity<List<OrderResponse>> getAllOrders() {

        return ResponseEntity.ok(
                orderService.getAllOrders()
        );
    }
}
