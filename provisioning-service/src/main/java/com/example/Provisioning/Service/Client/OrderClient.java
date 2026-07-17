package com.example.Provisioning.Service.Client;

import com.example.Provisioning.Service.ClientResponse.OrderResponse;
import com.example.Provisioning.Service.ClientResponse.OrderStatus;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("ORDER-SERVICE")
public interface OrderClient {
    @GetMapping("/orders/{orderNumber}")
    OrderResponse getOrder(@PathVariable("orderNumber") String orderNumber);

    @PatchMapping("/orders/{orderNumber}/status")
    void updateStatus(@PathVariable("orderNumber") String orderNumber, @RequestParam("status") OrderStatus status);
}
