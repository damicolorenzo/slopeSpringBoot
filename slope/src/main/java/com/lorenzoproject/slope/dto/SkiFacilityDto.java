package com.lorenzoproject.slope.dto;

import com.lorenzoproject.slope.enums.Status;
import com.lorenzoproject.slope.model.Image;
import com.lorenzoproject.slope.model.LiftStructure;
import com.lorenzoproject.slope.model.SkiRun;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class SkiFacilityDto {
    private Long id;
    private String name;
    private String type;
    private Status status;
    private BigDecimal dailyPrice;
    private Integer maxDailyCapacity;
    private List<LiftStructure> liftStructures;
    private List<SkiRun> skiRuns;
    private List<ImageDto> images = new ArrayList<>();
}
