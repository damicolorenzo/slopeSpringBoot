package com.lorenzoproject.slope.repository;

import com.lorenzoproject.slope.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUserId(Long id);
}
