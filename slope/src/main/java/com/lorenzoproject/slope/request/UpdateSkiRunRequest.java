package com.lorenzoproject.slope.request;

import com.lorenzoproject.slope.enums.DifficultyLevel;
import com.lorenzoproject.slope.enums.Status;
import lombok.Data;

@Data
public class UpdateSkiRunRequest {
    private String name;
    private DifficultyLevel difficulty;
    private Double lengthKm;
    private Status status;
}
