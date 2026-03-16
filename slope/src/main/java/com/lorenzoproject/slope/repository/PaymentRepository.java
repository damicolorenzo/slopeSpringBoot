package com.lorenzoproject.slope.repository;

import com.lorenzoproject.slope.enums.PaymentStatus;
import com.lorenzoproject.slope.model.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PaymentRepository extends JpaRepository<Payment, Long> {
    Optional<Payment> findByOrderId(Long orderId);
    List<Payment> findByStatus(PaymentStatus status);
}
