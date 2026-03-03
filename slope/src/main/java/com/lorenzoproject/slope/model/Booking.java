package com.lorenzoproject.slope.model;

import com.lorenzoproject.slope.enums.BookingTypes;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDateTime bookingDate;
    @Enumerated(EnumType.STRING)
    private BookingTypes type;
    private String status;

    @ManyToOne
    @JoinColumn(name = "buyer_user_id")
    private User buyer_user_id;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;
}
