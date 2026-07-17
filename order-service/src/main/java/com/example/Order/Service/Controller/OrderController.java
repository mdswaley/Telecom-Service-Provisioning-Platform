package com.example.Order.Service.Controller;

import com.example.Order.Service.Client.CustomerClient;
import com.example.Order.Service.Client.CustomerResponse;
import com.example.Order.Service.Entity.OrderStatus;
import com.example.Order.Service.OrderDTO.OrderRequest;
import com.example.Order.Service.OrderDTO.OrderResponse;
import com.example.Order.Service.Service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/orders")
public class OrderController {

    private final OrderService orderService;
    private final CustomerClient customerClient;

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

    @PatchMapping("/{orderNumber}/status")
    public ResponseEntity<String> updateStatus(@PathVariable String orderNumber, @RequestParam OrderStatus status) {
        orderService.updateOrderStatus(orderNumber, status);
        return ResponseEntity.ok("Status Updated");
    }

    @GetMapping("/test/{customerId}")
    public CustomerResponse testFeign(@PathVariable String customerId){
        return customerClient.getCustomer(customerId);
    }
}
