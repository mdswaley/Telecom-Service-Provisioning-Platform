package com.example.Provisioning.Service.Client;

import com.example.Provisioning.Service.DTO.NotificationRequest;
import com.example.Provisioning.Service.DTO.NotificationResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("NOTIFICATION-SERVICE")
public interface NotificationClient {
    @PostMapping("/notifications")
    NotificationResponse sendNotification(@RequestBody NotificationRequest request);

}
