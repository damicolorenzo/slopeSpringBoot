package com.lorenzoproject.slope.service.subscription;


import com.lorenzoproject.slope.model.Subscription;
import com.lorenzoproject.slope.model.User;

import java.math.BigDecimal;

public interface ISubscriptionService {
    Subscription purchaseSubscription(Long userId);

    boolean hasActiveSubscription(Long userId);

    BigDecimal applyDiscountIfAvailable(User user, BigDecimal amount);

    void cancelSubscription(Long subscriptionId);

    void checkAndExpireSubscriptions();
}
