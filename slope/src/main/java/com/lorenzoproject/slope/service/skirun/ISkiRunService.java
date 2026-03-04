package com.lorenzoproject.slope.service.skirun;

import com.lorenzoproject.slope.model.SkiFacility;
import com.lorenzoproject.slope.model.SkiRun;

import java.math.BigDecimal;

public interface ISkiRunService {
    SkiRun createSkiRun();
    SkiFacility getSkiFacilityFromId(Long id);

}
