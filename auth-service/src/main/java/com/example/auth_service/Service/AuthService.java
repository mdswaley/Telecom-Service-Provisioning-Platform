package com.example.auth_service.Service;

import com.example.auth_service.DTO.RegisterRequest;
import com.example.auth_service.DTO.RegisterResponse;
import com.example.auth_service.Entity.UserEntity;
import com.example.auth_service.Exceptions.ResourceNotFound;
import com.example.auth_service.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.management.relation.RelationServiceNotRegisteredException;

@Service
@RequiredArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final ModelMapper modelMapper;
    private final PasswordEncoder passwordEncoder;

    public RegisterResponse register(RegisterRequest registerRequest){
        boolean exist = userRepository.existsByEmail(registerRequest.getEmail());

        if(exist){
            throw new ResourceNotFound("User already exists with email: "+registerRequest.getEmail());
        }

        UserEntity createUser = modelMapper.map(registerRequest, UserEntity.class);
        createUser.setEmail(passwordEncoder.encode(createUser.getPassword()));

        UserEntity save = userRepository.save(createUser);

        return modelMapper.map(save, RegisterResponse.class);
    }

}
