package com.lorenzoproject.slope.repository;

import com.lorenzoproject.slope.model.Booking;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BookingRepository extends JpaRepository<Booking, Long> {
    List<Booking> getBookingsByUserId(Long userId);
    Booking getBookingById(Long bookingId);
}
