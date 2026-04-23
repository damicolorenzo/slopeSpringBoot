package com.lorenzoproject.slope.dto;

import com.lorenzoproject.slope.enums.DifficultyLevel;
import com.lorenzoproject.slope.enums.Status;
import lombok.Data;

import java.util.List;

@Data
public class SkiRunDto {
    private Long id;
    private String name;
    private String type;
    private Status status;
    private DifficultyLevel difficulty;
    private Double lengthKm;

    private SkiFacilityDto skiFacility;
    private List<ImageDto> images;
}
