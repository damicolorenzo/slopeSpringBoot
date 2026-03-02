package com.lorenzoproject.slope.model;

import com.lorenzoproject.slope.enums.Status;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

public class SkiRun {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private Status status;

    private SkiFacility skiFacility;
}
