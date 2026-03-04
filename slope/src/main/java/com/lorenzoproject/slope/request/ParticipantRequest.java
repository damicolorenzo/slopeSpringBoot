package com.lorenzoproject.slope.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ParticipantRequest {
    private String firstName;
    private String lastName;
    private LocalDate birthDate;
    private boolean insurance;
}
