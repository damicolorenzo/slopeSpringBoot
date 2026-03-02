package com.lorenzoproject.slope.model;

import com.lorenzoproject.slope.enums.Status;

public class LiftStructure {
    private Long id;
    private String name;
    private String type;
    private Status status;
    private int seats;

    private SkiFacility skiFacility;
}
