package com.lorenzoproject.slope.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Insurance {
    private Long id;
    private String type;
    private int duration;
    private BigDecimal price;
    private LocalDateTime insuranceDate;

    private RegisteredUser user;
    private Booking booking;
}
