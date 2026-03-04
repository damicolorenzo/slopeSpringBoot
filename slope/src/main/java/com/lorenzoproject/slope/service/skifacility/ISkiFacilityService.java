package com.lorenzoproject.slope.service.skifacility;

import com.lorenzoproject.slope.model.SkiFacility;

import java.math.BigDecimal;
import java.time.LocalDate;

public interface ISkiFacilityService {
    SkiFacility createSkiFacility();
    BigDecimal getDailyPriceBySkiFacilityId(Long id);
    void setDailyPriceForSkiFacility(Long id, BigDecimal price);
    void getAvailableFacilities();
    boolean checkOpeningDay(LocalDate day);
    boolean isAvailable(Long facilityId, LocalDate date, int participantsCount);
}
