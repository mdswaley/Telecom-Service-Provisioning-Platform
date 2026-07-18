package com.example.Provisioning.Service.Controller;

import com.example.Provisioning.Service.DTO.ProvisioningRequest;
import com.example.Provisioning.Service.DTO.ProvisioningResponse;
import com.example.Provisioning.Service.Entity.ProvisioningEntity;
import com.example.Provisioning.Service.Service.ProvisioningService;
import lombok.RequiredArgsConstructor;
import org.modelmapper.Provider;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/provisioning")
public class ProvisioningController {
    private final ProvisioningService provisioningService;

    @PostMapping("/process")
    public ResponseEntity<ProvisioningEntity> process(
            @RequestBody ProvisioningEntity request) {

        return ResponseEntity.ok(
                provisioningService.processOrder(request)
        );
    }

    @PostMapping
    public ResponseEntity<ProvisioningResponse> provision(@RequestBody ProvisioningRequest request) {
        return ResponseEntity.ok(provisioningService.provision(request));
    }
}
