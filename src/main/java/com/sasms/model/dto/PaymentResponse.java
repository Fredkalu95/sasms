package com.sasms.model.dto;

import com.sasms.model.entity.PaymentStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class PaymentResponse {

    private Long id;
    private Double amount;
    private PaymentStatus status;
    private LocalDateTime paymentDate;
    private Long bookingId;

    private String clientSecret; // Stripe client secret for frontend
}
