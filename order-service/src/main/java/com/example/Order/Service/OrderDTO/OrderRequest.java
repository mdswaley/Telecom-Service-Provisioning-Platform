package com.example.Order.Service.OrderDTO;

import com.example.Order.Service.Entity.OrderType;
import lombok.Data;

@Data
public class OrderRequest {
    private String customerId;
    private OrderType orderType;
}
