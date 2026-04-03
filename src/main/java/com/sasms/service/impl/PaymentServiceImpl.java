package com.sasms.service.impl;

import com.sasms.model.dto.PaymentResponse;
import com.sasms.model.entity.*;
import com.sasms.repository.BookingRepository;
import com.sasms.repository.PaymentRepository;
import com.sasms.service.PaymentService;
import com.sasms.service.StripeService;
import com.stripe.model.PaymentIntent;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class PaymentServiceImpl implements PaymentService {

    private final PaymentRepository paymentRepository;
    private final BookingRepository bookingRepository;
    private final StripeService stripeService;

    public PaymentServiceImpl(PaymentRepository paymentRepository, BookingRepository bookingRepository, StripeService stripeService) {
        this.paymentRepository = paymentRepository;
        this.bookingRepository = bookingRepository;
        this.stripeService = stripeService;
    }

    @Override
    public PaymentResponse initiatePayment(Long bookingId, Double amount) {
        Booking booking = bookingRepository.findById(bookingId)
                .orElseThrow(() -> new RuntimeException("Booking not found"));

        try {
            PaymentIntent intent = stripeService.createPaymentIntent(amount);

            Payment payment = Payment.builder()
                    .booking(booking)
                    .amount(amount)
                    .status(PaymentStatus.PENDING)
                    .stripePaymentIntentId(intent.getId())
                    .build();

            paymentRepository.save(payment);
            return PaymentResponse.builder()
                    .id(payment.getId())
                    .amount(payment.getAmount())
                    .status(payment.getStatus())
                    .bookingId(booking.getId())
                    .clientSecret(intent.getClientSecret())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Stripe payment failed");
        }
    }

    @Override
    public PaymentResponse confirmPayment(Long paymentId) {
        Payment payment = paymentRepository.findById(paymentId)
                .orElseThrow(() -> new RuntimeException("Payment not found"));

        payment.setStatus(PaymentStatus.COMPLETED);
        payment.setPaymentDate(LocalDateTime.now());

        Booking booking = payment.getBooking();
        booking.setStatus(Status.CONFIRMED);

        bookingRepository.save(booking);

        paymentRepository.save(payment);

        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paymentDate(payment.getPaymentDate())
                .bookingId(booking.getId())
                .build();
    }

    private PaymentResponse mapToResponse(Payment payment) {
        return PaymentResponse.builder()
                .id(payment.getId())
                .amount(payment.getAmount())
                .status(payment.getStatus())
                .paymentDate(payment.getPaymentDate())
                .bookingId(payment.getBooking().getId())
                .build();
    }

}
