package com.example.auth_service.Service;

import com.example.auth_service.DTO.LoginRequest;
import com.example.auth_service.DTO.LoginResponse;
import com.example.auth_service.DTO.RegisterRequest;
import com.example.auth_service.DTO.RegisterResponse;
import com.example.auth_service.Entity.UserEntity;
import com.example.auth_service.Exceptions.ResourceNotFound;
import com.example.auth_service.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final JWTService jwtService;

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

        return LoginResponse.builder()
                .accessToken(accessToken)
                .refreshToken(refreshToken)
                .email(user.getEmail())
                .build();
    }

}
