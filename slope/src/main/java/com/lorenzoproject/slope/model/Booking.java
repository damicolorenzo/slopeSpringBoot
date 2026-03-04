package com.lorenzoproject.slope.model;

import com.lorenzoproject.slope.enums.BookingStatus;
import com.lorenzoproject.slope.enums.BookingTypes;
import com.lorenzoproject.slope.enums.OrderStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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
    private BookingStatus status;
    private BigDecimal totalPrice;

    @ManyToOne
    @JoinColumn(name = "buyer_user_id")
    private User buyer;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    @ManyToOne
    @JoinColumn(name = "skifacility_id")
    private SkiFacility skiFacility;

    @OneToMany(mappedBy = "booking", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<BookingParticipant> participants = new HashSet<>();
}
