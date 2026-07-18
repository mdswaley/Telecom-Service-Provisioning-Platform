package com.example.Provisioning.Service.Config;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProvisioningConfig {
    @Bean
    public ModelMapper modelMapper(){
        return new ModelMapper();
    }
}
