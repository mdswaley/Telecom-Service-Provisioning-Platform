package com.example.auth_service.Controller;

import com.example.auth_service.DTO.*;
import com.example.auth_service.Entity.CustomUserDetails;
import com.example.auth_service.Entity.UserEntity;
import com.example.auth_service.Repository.UserRepository;
import com.example.auth_service.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {
    private final AuthService authService;
    private final UserRepository userRepository;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> signup(@RequestBody RegisterRequest registerRequest){
        RegisterResponse userReg = authService.register(registerRequest);
        return ResponseEntity.ok(userReg);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest loginRequest){
        LoginResponse userLogin = authService.login(loginRequest);
        return ResponseEntity.ok(userLogin);
    }

    @GetMapping("/profile")
    public ResponseEntity<UserProfileResponse> profile(Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        UserEntity user = userRepository
                .findByEmail(userDetails.getUsername())
                .orElseThrow();

        return ResponseEntity.ok(
                UserProfileResponse.builder()
                        .id(user.getId())
                        .fullName(user.getFullName())
                        .email(user.getEmail())
                        .role(user.getUserRole())
                        .build()
        );
    }
}
