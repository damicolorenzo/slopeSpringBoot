package com.lorenzoproject.slope.request;

import com.lorenzoproject.slope.enums.DifficultyLevel;
import com.lorenzoproject.slope.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.math.BigInteger;

@Data
public class AddLiftStructureRequest {
    private Long id;
    private String name;
    private String type;
    private Status status;
    private BigDecimal seats;
}
