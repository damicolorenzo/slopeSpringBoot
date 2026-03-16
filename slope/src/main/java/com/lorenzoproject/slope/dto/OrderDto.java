package com.lorenzoproject.slope.dto;

import com.lorenzoproject.slope.enums.OrderStatus;
import com.lorenzoproject.slope.model.Booking;
import com.lorenzoproject.slope.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class OrderDto {
    private Long id;
    private LocalDateTime orderDate;
    private BigDecimal totalAmount;
    private OrderStatus orderStatus;
    private Set<Booking> bookings;
    private User user;
}
