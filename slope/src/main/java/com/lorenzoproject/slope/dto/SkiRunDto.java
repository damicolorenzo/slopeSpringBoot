package com.lorenzoproject.slope.dto;

import com.lorenzoproject.slope.enums.DifficultyLevel;
import com.lorenzoproject.slope.enums.Status;
import com.lorenzoproject.slope.model.Image;
import com.lorenzoproject.slope.model.SkiFacility;
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

    private SkiFacility skiFacility;
    private List<ImageDto> images;
}
