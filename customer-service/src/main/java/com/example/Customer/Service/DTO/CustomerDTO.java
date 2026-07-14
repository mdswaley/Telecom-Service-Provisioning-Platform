package com.example.Customer.Service.DTO;

import lombok.Data;

@Data
public class CustomerDTO {
    private String customerId;
    private String fullName;
    private String email;
    private String phoneNumber;
    private String address;
    private String aadhaarNumber;
}
