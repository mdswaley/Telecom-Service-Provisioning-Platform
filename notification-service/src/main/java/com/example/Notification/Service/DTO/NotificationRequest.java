package com.example.Notification.Service.DTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationRequest {
    private String orderNumber;
    private String customerId;
    private String message;
}
