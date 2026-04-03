package com.sasms.service;

import com.sasms.model.dto.PaymentResponse;

public interface PaymentService {
    PaymentResponse initiatePayment(Long bookingId, Double amount);
    PaymentResponse confirmPayment(Long paymentId);
}
