package com.lorenzoproject.slope.service.payment;

public interface IPaymentService {
    String createCheckoutSession(Long orderId);
    void handleStripeWebhook(String payload, String sigHeader);
}
