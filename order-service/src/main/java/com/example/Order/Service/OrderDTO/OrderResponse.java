package com.example.Order.Service.OrderDTO;

import com.example.Order.Service.Entity.OrderStatus;
import com.example.Order.Service.Entity.OrderType;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class OrderResponse {
    private Long id;
    private String orderNumber;
    private Long customerId;
    private OrderType orderType;
    private OrderStatus status;
}
