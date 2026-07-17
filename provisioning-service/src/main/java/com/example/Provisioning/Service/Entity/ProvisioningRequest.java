package com.example.Provisioning.Service.Entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProvisioningRequest{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String provisioningId;

    private String orderNumber;

    private String simNumber;

    private String msisdn;

    @Enumerated(EnumType.STRING)
    private ProvisioningStatus status;

}
