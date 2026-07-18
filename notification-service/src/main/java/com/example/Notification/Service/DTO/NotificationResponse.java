package com.example.Notification.Service.DTO;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationResponse {
    private Long id;
    private String orderNumber;
    private String customerId;
    private String message;
    private NotificationStatus status;
}
