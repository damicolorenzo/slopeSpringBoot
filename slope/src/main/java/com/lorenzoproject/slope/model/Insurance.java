package com.lorenzoproject.slope.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lorenzoproject.slope.enums.InsuranceCoverageStatus;
import com.lorenzoproject.slope.enums.InsuranceStatus;
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
    @Enumerated(EnumType.STRING)
    private InsuranceCoverageStatus coverageType;
    @Enumerated(EnumType.STRING)
    private InsuranceStatus status;
    private LocalDateTime createdAt;

    @OneToOne
    @JoinColumn(name = "booking_participant_id")
    @JsonIgnore
    private BookingParticipant bookingParticipant;



}
