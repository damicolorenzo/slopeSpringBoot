package com.lorenzoproject.slope.service.subscription;

import com.lorenzoproject.slope.model.Subscription;

import java.math.BigDecimal;

public interface ISubscriptionService {
    Subscription createSubscription();
    void renewSubscriptionUsingSubscriptionId(Long id);
    boolean checkActiveSubscription(Long id);
    void cancelSubscription(Long id);
    BigDecimal calculateDiscount();
}
