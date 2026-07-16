package com.example.Provisioning.Service.Controller;

import com.example.Provisioning.Service.Entity.ProvisioningRequest;
import com.example.Provisioning.Service.Service.ProvisioningService;
import lombok.RequiredArgsConstructor;
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
    public ResponseEntity<ProvisioningRequest> process(
            @RequestBody ProvisioningRequest request) {

        return ResponseEntity.ok(
                provisioningService.processOrder(request)
        );
    }
}
