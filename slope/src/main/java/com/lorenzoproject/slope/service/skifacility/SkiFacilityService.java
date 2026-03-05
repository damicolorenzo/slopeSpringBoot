package com.lorenzoproject.slope.service.skifacility;

import com.lorenzoproject.slope.enums.Status;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.model.SkiFacility;
import com.lorenzoproject.slope.repository.SkiFacilityRepository;
import com.lorenzoproject.slope.request.CreateFacilityRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkiFacilityService implements ISkiFacilityService{
    private final SkiFacilityRepository skiFacilityRepository;

    @Override
    public SkiFacility createFacility(CreateFacilityRequest request) {
        SkiFacility facility = new SkiFacility();
        facility.setName(request.getName());
        facility.setDailyPrice(request.getDailyPrice());
        facility.setMaxDailyCapacity(request.getMaxDailyCapacity());
        facility.setStatus(Status.OPEN);
        return skiFacilityRepository.save(facility);
    }

    @Override
    public SkiFacility updatePrice(Long facilityId, BigDecimal newPrice) {
        SkiFacility facility = skiFacilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));
        facility.setDailyPrice(newPrice);
        return facility;
    }

    @Override
    public void deactivateFacility(Long facilityId) {
        SkiFacility facility = skiFacilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));
        facility.setStatus(Status.CLOSED);
    }

    @Override
    public List<SkiFacility> getActiveFacilities() {
        return skiFacilityRepository.findByStatus(Status.OPEN);
    }

    @Override
    public SkiFacility getFacilityForBooking(Long facilityId) {
        SkiFacility facility = skiFacilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));
        if(facility.getStatus() == Status.CLOSED) {
            throw new IllegalStateException("Facility is not active");
        }
        return facility;
    }
}
