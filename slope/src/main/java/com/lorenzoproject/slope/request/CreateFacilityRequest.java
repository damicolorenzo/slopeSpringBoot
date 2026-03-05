package com.lorenzoproject.slope.request;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class CreateFacilityRequest {
    private String name;
    private BigDecimal dailyPrice;
    private Integer maxDailyCapacity;
}
