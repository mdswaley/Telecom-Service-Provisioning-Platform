package com.example.auth_service.DTO;

import lombok.Data;

@Data
public class RegisterResponse {
    private Long id;
    private String fullName;
    private String email;
}
