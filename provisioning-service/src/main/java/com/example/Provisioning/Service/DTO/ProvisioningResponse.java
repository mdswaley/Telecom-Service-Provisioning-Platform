package com.example.Provisioning.Service.DTO;

import com.example.Provisioning.Service.Entity.ProvisioningStatus;
import lombok.Data;

@Data
public class ProvisioningResponse {
    private String provisioningId;
    private String orderNumber;
    private String simNumber;
    private String msisdn;
    private ProvisioningStatus status;
}
