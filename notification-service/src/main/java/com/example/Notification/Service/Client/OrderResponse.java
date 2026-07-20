package com.example.Notification.Service.Client;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private String customerId;
    private OrderType orderType;
}
