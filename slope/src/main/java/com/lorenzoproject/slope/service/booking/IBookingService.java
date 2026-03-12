package com.lorenzoproject.slope.service.booking;

import com.lorenzoproject.slope.model.Booking;
import com.lorenzoproject.slope.model.Order;
import com.lorenzoproject.slope.request.CreateBookingRequest;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public interface IBookingService {
    Order createBooking(CreateBookingRequest request, Long userId);
    List<Booking> getBookingsByUserId(Long userId);
    void cancelBookingById(Long userId);
    Booking getBookingById(Long bookingId);
}
