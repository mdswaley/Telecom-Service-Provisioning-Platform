package com.example.auth_service.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class LoginResponse {
    private String email;
    private String accessToken;
    private String refreshToken;

    public LoginResponse(String accessToken, String email) {
        this.accessToken = accessToken;
        this.email = email;
    }
}
