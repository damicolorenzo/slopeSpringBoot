package com.lorenzoproject.slope.service.booking;

import com.lorenzoproject.slope.model.Order;
import com.lorenzoproject.slope.model.User;
import com.lorenzoproject.slope.request.CreateBookingRequest;

import java.math.BigDecimal;

public interface IBookingService {
    Order createBooking(CreateBookingRequest request, Long userId);
    BigDecimal applySubscriptionDiscount(User user, BigDecimal totalPrice);
}
