package com.lorenzoproject.slope.dto;

import com.lorenzoproject.slope.enums.DifficultyLevel;
import com.lorenzoproject.slope.enums.Status;
import com.lorenzoproject.slope.model.SkiFacility;
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

    private SkiFacility skiFacility;
    private List<ImageDto> images;
}
