package com.lorenzoproject.slope.dto;

import com.lorenzoproject.slope.enums.BookingStatus;
import com.lorenzoproject.slope.enums.BookingTypes;
import com.lorenzoproject.slope.model.BookingParticipant;
import com.lorenzoproject.slope.model.Order;
import com.lorenzoproject.slope.model.SkiFacility;
import com.lorenzoproject.slope.model.User;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

@Data
public class BookingDto {
    private Long id;
    private LocalDateTime bookingDate;
    private BookingTypes type;
    private BookingStatus status;
    private BigDecimal totalPrice;
    private User buyer;
    private Order order;
    private SkiFacility skiFacility;
    private Set<BookingParticipant> participants;
}
