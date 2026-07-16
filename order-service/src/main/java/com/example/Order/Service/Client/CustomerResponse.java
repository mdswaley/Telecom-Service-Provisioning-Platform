package com.example.Order.Service.Client;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CustomerResponse {
    private Long id;
    private String customerId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String aadhaarNumber;
}
