package com.example.Notification.Service.Entity;

import com.example.Notification.Service.DTO.NotificationStatus;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NotificationEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String orderNumber;

    private String customerId;

    private String message;

    @Enumerated(EnumType.STRING)
    private NotificationStatus status;
}
