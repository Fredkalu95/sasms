package com.sasms.controller;

import com.sasms.model.dto.PaymentResponse;
import com.sasms.model.entity.Payment;
import com.sasms.service.PaymentService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payment")
public class PaymentController {
    private final PaymentService paymentService;


    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/initiate/{bookingId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentResponse> initiatePayment(
        @PathVariable Long bookingId,
        @RequestParam Double amount
    ) {
        return ResponseEntity.status(201).body(paymentService.initiatePayment(bookingId, amount));
    }

    @PostMapping("/confirm/{paymentId}")
    @PreAuthorize("hasRole('USER')")
    public ResponseEntity<PaymentResponse> confirmPayment(
            @PathVariable Long paymentId
    ) {
        return ResponseEntity.ok(paymentService.confirmPayment(paymentId));
    }
}
