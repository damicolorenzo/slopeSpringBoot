package com.lorenzoproject.slope.controller;

import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.model.Booking;
import com.lorenzoproject.slope.model.Order;
import com.lorenzoproject.slope.request.CreateBookingRequest;
import com.lorenzoproject.slope.response.ApiResponse;
import com.lorenzoproject.slope.service.booking.IBookingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/bookings")
public class BookingController {
    private final IBookingService bookingService;

    @PostMapping("/booking")
    public ResponseEntity<ApiResponse> createBooking(@RequestBody CreateBookingRequest request, @PathVariable Long userId) {
        Order order = bookingService.createBooking(request, userId);
        return ResponseEntity.ok(new ApiResponse("Booking created", order));
    }

    @GetMapping("/booking/{userId}/bookings")
    public ResponseEntity<ApiResponse> getUserBookings(@PathVariable Long userId) {
        List<Booking> bookings = bookingService.getBookingsByBuyerId(userId);
        return ResponseEntity.ok(new ApiResponse("Success", bookings));
    }

    @DeleteMapping("/booking/{bookingId}/delete")
    public ResponseEntity<ApiResponse> cancelBooking(@PathVariable Long bookingId) {
        try {
            bookingService.cancelBookingById(bookingId);
            return ResponseEntity.ok(new ApiResponse("Delete success", bookingId));
        } catch(ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }

    }
}
