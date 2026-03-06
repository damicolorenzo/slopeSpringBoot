package com.lorenzoproject.slope.service.skifacility;

import com.lorenzoproject.slope.dto.SkiFacilityDto;
import com.lorenzoproject.slope.model.SkiFacility;
import com.lorenzoproject.slope.request.AddSkiFacilityRequest;
import com.lorenzoproject.slope.request.CreateFacilityRequest;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ISkiFacilityService {
    SkiFacility addSkiFacility(AddSkiFacilityRequest request);

    SkiFacility createFacility(AddSkiFacilityRequest request);

    SkiFacility updatePrice(Long facilityId, BigDecimal newPrice);

    void deactivateFacility(Long facilityId);

    List<SkiFacility> getActiveFacilities();

    SkiFacility getFacilityForBooking(Long facilityId);

    SkiFacility getSkiFacilityById(Long id);

    List<SkiFacilityDto> getConvertedSkiFacilities(List<SkiFacility> skiFacilities);

    SkiFacilityDto convertToDto(SkiFacility skiFacility);
}
