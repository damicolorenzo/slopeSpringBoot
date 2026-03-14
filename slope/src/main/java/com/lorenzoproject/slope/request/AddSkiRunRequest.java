package com.lorenzoproject.slope.request;

import com.lorenzoproject.slope.enums.DifficultyLevel;
import com.lorenzoproject.slope.enums.Status;
import lombok.Data;

@Data
public class AddSkiRunRequest {
    private String name;
    private String type;
    private Status status;
    private DifficultyLevel difficulty;
    private Double lengthKm;
}
