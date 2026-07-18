package com.example.Provisioning.Service.DTO;
import com.example.Provisioning.Service.ClientResponse.NotificationStatus;
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
