package com.example.auth_service.Controller;

import com.example.auth_service.DTO.RegisterRequest;
import com.example.auth_service.DTO.RegisterResponse;
import com.example.auth_service.Service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/auth/v1")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> signup(@RequestBody RegisterRequest registerRequest){
        RegisterResponse userReg = authService.register(registerRequest);
        return ResponseEntity.ok(userReg);

    }
}
