package com.example.Notification.Service.Client;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Builder
public class CustomerResponse {
    private Long id;
    private String customerId;
    private String email;
    private String phoneNumber;
}
