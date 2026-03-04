package com.lorenzoproject.slope.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Insurance {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private BigDecimal price;
    private String coverageType;
    private String status;
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "booking_participant_id")
    private BookingParticipant bookingParticipant;



}
