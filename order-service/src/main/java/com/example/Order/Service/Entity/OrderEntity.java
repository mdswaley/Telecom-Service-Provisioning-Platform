package com.example.Order.Service.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    private String customerId;

    @Enumerated(EnumType.STRING)
    private OrderType orderType;

    @Enumerated(EnumType.STRING)
    private OrderStatus status;
}
