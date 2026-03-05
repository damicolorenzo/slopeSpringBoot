package com.lorenzoproject.slope.request;

import com.lorenzoproject.slope.enums.DifficultyLevel;
import lombok.Data;

@Data
public class CreateSkiRunRequest {
    private String name;
    private DifficultyLevel difficulty;
    private Double lengthKm;
}
