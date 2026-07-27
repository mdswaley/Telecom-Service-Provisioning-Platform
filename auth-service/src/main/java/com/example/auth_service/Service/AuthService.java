package com.example.auth_service.Service;

import com.example.auth_service.DTO.LoginRequest;
import com.example.auth_service.DTO.LoginResponse;
import com.example.auth_service.DTO.RegisterRequest;
import com.example.auth_service.DTO.RegisterResponse;
import com.example.auth_service.Entity.RefreshToken;
import com.example.auth_service.Entity.UserEntity;
import com.example.auth_service.Exceptions.ResourceNotFound;
import com.example.auth_service.Repository.RefreshTokenRepository;
import com.example.auth_service.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;
    private final RefreshTokenRepository refreshTokenRepository;

    public RegisterResponse register(RegisterRequest registerRequest){
        boolean exist = userRepository.existsByEmail(registerRequest.getEmail());

        if(exist){
            throw new ResourceNotFound("User already exists with email: "+registerRequest.getEmail());
        }

        UserEntity createUser = modelMapper.map(registerRequest, UserEntity.class);
        createUser.setPassword(passwordEncoder.encode(createUser.getPassword()));

        UserEntity save = userRepository.save(createUser);

        return modelMapper.map(save, RegisterResponse.class);
    }

    public LoginResponse login(LoginRequest loginRequest){
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginRequest.getEmail(),
                loginRequest.getPassword()
        ));

        UserEntity user = userRepository.findByEmail(loginRequest.getEmail()).orElseThrow(
                ()-> new ResourceNotFound("User is not present with given email : "+loginRequest.getEmail())
        );

        String accessToken = jwtService.generateAccessToken(user);
        String refreshToken = jwtService.generateRefreshToken(user);

        RefreshToken token = new RefreshToken();
        token.setToken(refreshToken);
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusDays(7));
        token.setRevoked(false);

        refreshTokenRepository.save(token);

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .build();
    }

    public LoginResponse refreshToken(String refreshToken) {

        RefreshToken storedToken = refreshTokenRepository.findByToken(refreshToken)
                .orElseThrow(() -> new RuntimeException("Invalid Refresh Token"));

        if (storedToken.isRevoked()) {
            throw new RuntimeException("Refresh Token has been revoked");
        }

        if (!jwtService.isRefreshTokenValid(refreshToken)) {
            throw new RuntimeException("Refresh Token expired");
        }

        UserEntity user = storedToken.getUser();

        // Revoke old token
        storedToken.setRevoked(true);
        refreshTokenRepository.save(storedToken);

        // Generate new tokens
        String newAccessToken = jwtService.generateAccessToken(user);
        String newRefreshToken = jwtService.generateRefreshToken(user);

        // Save new refresh token
        RefreshToken token = new RefreshToken();
        token.setToken(newRefreshToken);
        token.setUser(user);
        token.setExpiryDate(LocalDateTime.now().plusDays(7));
        token.setRevoked(false);

        refreshTokenRepository.save(token);

        return LoginResponse.builder()
                .accessToken(newAccessToken)
                .refreshToken(newRefreshToken)
                .email(user.getEmail())
                .build();
    }
}
