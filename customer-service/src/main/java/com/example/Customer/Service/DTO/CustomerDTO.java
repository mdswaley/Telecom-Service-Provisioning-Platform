package com.example.Customer.Service.DTO;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CustomerDTO {
    private Long id;
    private String customerId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String aadhaarNumber;
}
