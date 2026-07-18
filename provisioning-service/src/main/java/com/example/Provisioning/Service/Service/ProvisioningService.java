package com.example.Provisioning.Service.Service;

import com.example.Provisioning.Service.Client.OrderClient;
import com.example.Provisioning.Service.ClientResponse.OrderResponse;
import com.example.Provisioning.Service.ClientResponse.OrderStatus;
import com.example.Provisioning.Service.DTO.ProvisioningRequest;
import com.example.Provisioning.Service.Entity.ProvisioningEntity;
import com.example.Provisioning.Service.DTO.ProvisioningResponse;
import com.example.Provisioning.Service.Entity.ProvisioningStatus;
import com.example.Provisioning.Service.Repository.ProvisioningRepository;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

@Service
@RequiredArgsConstructor
public class ProvisioningService {

    private final ProvisioningRepository repository;
    private final OrderClient orderClient;
    private final ModelMapper modelMapper;

    public ProvisioningEntity processOrder(ProvisioningEntity request) {
        request.setStatus(ProvisioningStatus.IN_PROGRESS);
        return repository.save(request);
    }



    public ProvisioningResponse provision(ProvisioningRequest request) {

        OrderResponse order =
                orderClient.getOrder(
                        request.getOrderNumber());

        ProvisioningEntity entity = ProvisioningEntity.builder()
                        .provisioningId(
                                "PROV-" +
                                        UUID.randomUUID()
                                                .toString()
                                                .substring(0, 8))
                        .orderNumber(
                                order.getOrderNumber())
                        .simNumber(
                                generateSimNumber())
                        .msisdn(
                                generateMsisdn())
                        .status(
                                ProvisioningStatus.COMPLETED)
                        .build();

        ProvisioningEntity saved = repository.save(entity);

        orderClient.updateStatus(order.getOrderNumber(), OrderStatus.COMPLETED);
        return modelMapper.map(saved, ProvisioningResponse.class);
    }

    private String generateSimNumber() {

        return "SIM" + ThreadLocalRandom.current()
                                .nextLong(
                                1000000000L,
                                9999999999L);
    }

    private String generateMsisdn() {

        return "91" + ThreadLocalRandom.current()
                        .       nextLong(
                                1000000000L,
                                9999999999L);
    }
}
