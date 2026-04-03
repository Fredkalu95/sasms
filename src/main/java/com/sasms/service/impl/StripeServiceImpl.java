package com.sasms.service.impl;

import com.sasms.service.StripeService;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StripeServiceImpl implements StripeService {

    @Value("${stripe.secret.key}")
    private String stripeSecretKey;

    @Override
    public PaymentIntent createPaymentIntent(Double amount) throws StripeException {

        Stripe.apiKey = stripeSecretKey;

        PaymentIntentCreateParams params =
                PaymentIntentCreateParams.builder()
                        .setAmount((long) (amount * 100))
                        .setCurrency("usd")
                        .addPaymentMethodType("card")
                        .build();

        return PaymentIntent.create(params);
    }
}
