package com.example.Notification.Service.Service;

import com.example.Notification.Service.Client.CustomerClient;
import com.example.Notification.Service.Client.CustomerResponse;
import com.example.Notification.Service.DTO.NotificationRequest;
import com.example.Notification.Service.DTO.NotificationResponse;
import com.example.Notification.Service.DTO.NotificationStatus;
import com.example.Notification.Service.Entity.NotificationEntity;
import com.example.Notification.Service.Repository.NotificationRepo;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class NotificationService {
    private final NotificationRepo repository;
    private final ModelMapper modelMapper;
    private final CustomerClient customerClient;
    private final EmailService emailService;

    public NotificationResponse sendNotification(NotificationRequest request) {

        NotificationEntity entity = NotificationEntity.builder()
                        .orderNumber(request.getOrderNumber())
                        .customerId(request.getCustomerId())
                        .message(request.getMessage())
                        .status(NotificationStatus.SENT)
                        .build();

        NotificationEntity saved = repository.save(entity);

        CustomerResponse customer = customerClient.getCustomer(request.getCustomerId());
        emailService.sendEmail(customer.getEmail(),
               saved.getMessage(),
                request.getMessage());

        return modelMapper.map(saved, NotificationResponse.class);
    }

    public List<NotificationResponse> getAllNotifications() {

        return repository.findAll().stream()
                .map(entity ->
                        modelMapper.map(
                                entity,
                                NotificationResponse.class))
                .toList();
    }
}
