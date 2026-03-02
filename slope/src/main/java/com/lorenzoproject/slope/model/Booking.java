package com.lorenzoproject.slope.model;

import com.lorenzoproject.slope.enums.BookingTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Booking {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private LocalDateTime bookingDate;
    private BookingTypes type;

    private RegisteredUser user;
}
