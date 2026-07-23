package com.example.Customer.Service.Advice;

import lombok.*;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ApiError {
    private LocalDateTime timeStamp;
    private String error;
    private HttpStatus statusCode;
}
