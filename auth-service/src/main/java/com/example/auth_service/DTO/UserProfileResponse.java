package com.example.auth_service.DTO;

import com.example.auth_service.Enums.UserRole;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserProfileResponse {

    private Long id;
    private String fullName;
    private String email;
    private UserRole role;
}
