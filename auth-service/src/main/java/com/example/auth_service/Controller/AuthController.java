package com.example.auth_service.Controller;

import com.example.auth_service.DTO.*;
import com.example.auth_service.Entity.CustomUserDetails;
import com.example.auth_service.Entity.UserEntity;
import com.example.auth_service.Repository.UserRepository;
import com.example.auth_service.Service.AuthService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;

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
    public ResponseEntity<LoginResponse> signIn(@RequestBody LoginRequest loginRequest, HttpServletResponse response){
        LoginResponse loginResponseDto = authService.login(loginRequest);

        Cookie cookie = new Cookie("refreshToken",loginResponseDto.getRefreshToken());
        cookie.setHttpOnly(true); // only we can see not other like attackers
//        cookie.setSecure("production".equals(devEnv));
        response.addCookie(cookie);

        return ResponseEntity.ok(
                new LoginResponse(
                        loginResponseDto.getAccessToken(),
                        loginResponseDto.getEmail()
                )
        );
    }

    @PostMapping("/refresh")
    public ResponseEntity<LoginResponse> refreshToken(
            @CookieValue("refreshToken") String refreshToken) {
        LoginResponse res = authService.refreshToken(refreshToken);

        return ResponseEntity.ok(
                new LoginResponse(res.getAccessToken(), res.getEmail()
        ));
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

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String admin() {
        return "Admin Access";
    }

    @GetMapping("/customer")
    @PreAuthorize("hasRole('CUSTOMER')")
    public String customer() {
        return "Customer Access";
    }
}
