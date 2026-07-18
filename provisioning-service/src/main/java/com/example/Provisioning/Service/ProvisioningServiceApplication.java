package com.example.Provisioning.Service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableFeignClients
public class ProvisioningServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProvisioningServiceApplication.class, args);
	}

}
