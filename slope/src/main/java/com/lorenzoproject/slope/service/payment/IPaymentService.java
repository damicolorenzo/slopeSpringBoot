package com.lorenzoproject.slope.service.payment;

import com.lorenzoproject.slope.dto.PaymentDto;
import com.lorenzoproject.slope.model.Payment;
import com.lorenzoproject.slope.request.PaymentRequest;

public interface IPaymentService {
    PaymentDto initiatePayment(Long orderId, PaymentRequest request);
    PaymentDto processPayment(Long paymentId);
    void cancelPayment(Long orderId);
    PaymentDto getPaymentByOrderId(Long orderId);
    PaymentDto convertToDto(Payment payment);
}
