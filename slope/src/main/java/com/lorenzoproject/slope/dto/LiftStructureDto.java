package com.lorenzoproject.slope.dto;

import com.lorenzoproject.slope.enums.Status;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

@Data
public class LiftStructureDto {
    private Long id;
    private String name;
    private String type;
    private Status status;
    private BigDecimal seats;

    private SkiFacilityDto skiFacility;
    private List<ImageDto> images;
}
