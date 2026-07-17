package com.example.Provisioning.Service.ClientResponse;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private String customerId;
    private OrderType orderType;
    private OrderStatus status;
}
