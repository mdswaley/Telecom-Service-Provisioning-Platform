package com.example.Notification.Service.Service;

import com.example.Notification.Service.Client.CustomerClient;
import com.example.Notification.Service.Client.CustomerResponse;
import com.example.Notification.Service.Client.OrderClient;
import com.example.Notification.Service.Client.OrderResponse;
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
    private final OrderClient orderClient;
    private final EmailService emailService;


    public NotificationResponse sendNotification(NotificationRequest request) {

        NotificationEntity entity = NotificationEntity.builder()
                        .orderNumber(request.getOrderNumber())
                        .customerId(request.getCustomerId())
                        .message(request.getMessage())
                        .status(NotificationStatus.SENT)
                        .build();

        NotificationEntity saved = repository.save(entity);
        OrderResponse order = orderClient.getOrder(request.getOrderNumber());

        CustomerResponse customer = customerClient.getCustomer(request.getCustomerId());
        emailService.sendEmail(customer.getEmail(),
               saved.getMessage(),
                getEmailBody(order, customer));

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

    private String getEmailBody(OrderResponse order,
                                CustomerResponse customer) {

        return switch (order.getOrderType()) {

            case NEW_SIM ->
                    """
                    Dear %s,
    
                    Congratulations! Your new SIM has been activated successfully.
    
                    Order Number : %s
                    Customer ID  : %s
    
                    You can now start using our telecom services.
    
                    Regards,
                    Telecom Support Team
                    """.formatted(
                            customer.getFullName(),
                            order.getOrderNumber(),
                            order.getCustomerId());

            case PORT_IN ->
                    """
                    Dear %s,
    
                    Your mobile number porting request has been completed successfully.
    
                    Order Number : %s
                    Customer ID  : %s
    
                    Welcome to our network.
    
                    Regards,
                    Telecom Support Team
                    """.formatted(
                            customer.getFullName(),
                            order.getOrderNumber(),
                            order.getCustomerId());

            case SIM_REPLACEMENT ->
                    """
                    Dear %s,
    
                    Your SIM replacement request has been completed successfully.
    
                    Order Number : %s
                    Customer ID  : %s
    
                    Please insert the new SIM card and restart your device.
    
                    Regards,
                    Telecom Support Team
                    """.formatted(
                            customer.getFullName(),
                            order.getOrderNumber(),
                            order.getCustomerId());

            case ROAMING ->
                    """
                    Dear %s,
    
                    International Roaming has been activated successfully.
    
                    Order Number : %s
                    Customer ID  : %s
    
                    You can now use your services while travelling abroad.
    
                    Regards,
                    Telecom Support Team
                    """.formatted(
                            customer.getFullName(),
                            order.getOrderNumber(),
                            order.getCustomerId());

            case DATA_PACK ->
                    """
                    Dear %s,
    
                    Your requested Data Pack has been activated successfully.
    
                    Order Number : %s
                    Customer ID  : %s
    
                    Enjoy high-speed internet services.
    
                    Regards,
                    Telecom Support Team
                    """.formatted(
                            customer.getFullName(),
                            order.getOrderNumber(),
                            order.getCustomerId());
        };
    }
}
