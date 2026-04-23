package com.lorenzoproject.slope.request;

import com.lorenzoproject.slope.enums.Status;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class AddLiftStructureRequest {
    private String name;
    private String type;
    private Status status;
    private BigDecimal seats;
}
