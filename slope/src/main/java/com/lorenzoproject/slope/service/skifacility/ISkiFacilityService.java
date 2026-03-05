package com.lorenzoproject.slope.service.skifacility;

import com.lorenzoproject.slope.model.SkiFacility;
import com.lorenzoproject.slope.request.CreateFacilityRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ISkiFacilityService {
    SkiFacility createFacility(CreateFacilityRequest request);

    SkiFacility updatePrice(Long facilityId, BigDecimal newPrice);

    void deactivateFacility(Long facilityId);

    List<SkiFacility> getActiveFacilities();

    SkiFacility getFacilityForBooking(Long facilityId);
}
