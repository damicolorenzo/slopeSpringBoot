package com.lorenzoproject.slope.request;

import com.lorenzoproject.slope.enums.DifficultyLevel;
import com.lorenzoproject.slope.enums.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class UpdateLiftStructureRequest {
    private String name;
    private String type;
    private BigDecimal seats;
    private Status status;
}
