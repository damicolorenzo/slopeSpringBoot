package com.lorenzoproject.slope.service.subscription;

import com.lorenzoproject.slope.enums.SubscriptionStatus;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.model.SkiRun;
import com.lorenzoproject.slope.model.Subscription;
import com.lorenzoproject.slope.model.User;
import com.lorenzoproject.slope.repository.SubscriptionRepository;
import com.lorenzoproject.slope.repository.UserRepository;
import com.lorenzoproject.slope.request.CreateSkiRunRequest;
import com.lorenzoproject.slope.request.UpdateSkiRunRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SubscriptionService implements ISubscriptionService{
    private final SubscriptionRepository subscriptionRepository;
    private final UserRepository userRepository;

    @Override
    public Subscription purchaseSubscription(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        if(hasActiveSubscription(userId)) {
            throw new IllegalStateException("User already has an active subscription");
        }

        Subscription subscription = new Subscription();
        subscription.setUser(user);
        subscription.setDiscountPercentage(resolveDiscount());
        subscription.setStartDate(LocalDate.now());
        subscription.setEndDate(resolveEndDate());
        subscription.setStatus(SubscriptionStatus.ACTIVE);

        return subscriptionRepository.save(subscription);
    }

    @Override
    public boolean hasActiveSubscription(Long userId) {
        return subscriptionRepository
                .existsByUserIdAndStatus(userId, SubscriptionStatus.ACTIVE);
    }

    @Override
    public BigDecimal applyDiscountIfAvailable(User user, BigDecimal amount) {
        Optional<Subscription> subscriptionOpt = subscriptionRepository
                .findByUserIdAndStatus(user.getId(), SubscriptionStatus.ACTIVE);
        if(subscriptionOpt.isEmpty())
            return amount;
        Subscription subscription = subscriptionOpt.get();
        if(subscription.getEndDate().isBefore(LocalDate.now())) {
            subscription.setStatus(SubscriptionStatus.EXPIRED);
            return amount;
        }
        BigDecimal discount = amount
                .multiply(subscription
                        .getDiscountPercentage()
                        .divide(BigDecimal.valueOf(100)));
        return amount.subtract(discount);
    }

    @Override
    public void cancelSubscription(Long subscriptionId) {
        Subscription subscription = subscriptionRepository.findById(subscriptionId)
                .orElseThrow(() -> new ResourceNotFoundException("Subscription not found"));
        subscription.setStatus(SubscriptionStatus.CANCELLED);
    }

    @Override
    public void checkAndExpireSubscriptions() {
        List<Subscription> activeSubscriptions = subscriptionRepository.findByStatus(SubscriptionStatus.ACTIVE);
        for(Subscription subscription : activeSubscriptions) {
            if(subscription.getEndDate().isBefore(LocalDate.now())) {
                subscription.setStatus(SubscriptionStatus.EXPIRED);
            }
        }
    }

    private BigDecimal resolveDiscount() {
        return BigDecimal.valueOf(10);
    }

    private LocalDate resolveEndDate() {
        return LocalDate.now().plusMonths(1);
    }
}
