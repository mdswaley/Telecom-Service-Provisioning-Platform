package com.example.Provisioning.Service.Service;

import com.example.Provisioning.Service.Entity.ProvisioningRequest;
import com.example.Provisioning.Service.Entity.ProvisioningStatus;
import com.example.Provisioning.Service.Repository.ProvisioningRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProvisioningService {

    private final ProvisioningRepository repository;

    public ProvisioningRequest processOrder(ProvisioningRequest request) {
        request.setStatus(ProvisioningStatus.IN_PROGRESS);
        return repository.save(request);
    }
}
