package com.example.Order.Service.Client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "customer-service")
public interface CustomerClient {
    @GetMapping("/customers/v2/get/{customerId}")
    CustomerResponse getCustomer(@PathVariable String customerId);
}
