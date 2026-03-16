package com.lorenzoproject.slope.request;

import com.lorenzoproject.slope.enums.PaymentMethod;
import lombok.Data;

@Data
public class PaymentRequest {
    private PaymentMethod paymentMethod;
    private String cardNumber;
    private String cardHolderName;
    private String cvv;
    private String expiryDate;
    private String paypalEmail;
}
