package com.lorenzoproject.slope.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class UserUpdateRequest {
    private String firstName;
    private String lastName;
    private String email;
    private String phoneNumber;
    private LocalDate birthDate;
    private String username;
    private String password;
}
