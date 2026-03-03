package com.lorenzoproject.slope.repository;

import com.lorenzoproject.slope.model.Order;

import java.util.List;

public interface OrderRepository {
    List<Order> findByUserId(Long userId);
}
