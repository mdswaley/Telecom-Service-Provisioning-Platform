package com.example.Provisioning.Service.Repository;

import com.example.Provisioning.Service.Entity.ProvisioningRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProvisioningRepository extends JpaRepository<ProvisioningRequest, Long> {
}
