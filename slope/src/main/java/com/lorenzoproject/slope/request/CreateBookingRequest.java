package com.lorenzoproject.slope.request;

import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Data
public class CreateBookingRequest {
    private Long skiFacilityId;
    private LocalDateTime bookingDate;
    private List<ParticipantRequest> participants;
}
