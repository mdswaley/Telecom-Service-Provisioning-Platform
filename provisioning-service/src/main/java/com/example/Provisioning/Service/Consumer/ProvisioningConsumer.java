package com.example.Provisioning.Service.Consumer;

import com.example.Provisioning.Service.Service.ProvisioningService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProvisioningConsumer {
    private final ProvisioningService provisioningService;

    @KafkaListener(
            topics = "provisioning-request",
            groupId = "provisioning-group"
    )
    public void consume(String orderNumber) {
        System.out.println("Received Order: " + orderNumber);
        provisioningService.processOrder(orderNumber);
    }
}
