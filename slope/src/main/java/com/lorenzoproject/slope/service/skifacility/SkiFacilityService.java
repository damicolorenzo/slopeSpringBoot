package com.lorenzoproject.slope.service.skifacility;

import com.lorenzoproject.slope.dto.ImageDto;
import com.lorenzoproject.slope.dto.SkiFacilityDto;
import com.lorenzoproject.slope.enums.Status;
import com.lorenzoproject.slope.exceptions.AlreadyExistsException;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.exceptions.SkiFacilityNotFoundException;
import com.lorenzoproject.slope.model.Image;
import com.lorenzoproject.slope.model.SkiFacility;
import com.lorenzoproject.slope.repository.ImageRepository;
import com.lorenzoproject.slope.repository.SkiFacilityRepository;
import com.lorenzoproject.slope.request.AddSkiFacilityRequest;
import com.lorenzoproject.slope.request.CreateFacilityRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SkiFacilityService implements ISkiFacilityService{
    private final SkiFacilityRepository skiFacilityRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    public SkiFacility addSkiFacility(AddSkiFacilityRequest request) {
        if(skiFacilityExists(request.getName())) {
            throw new AlreadyExistsException(request.getName() + " already exists, you may update this ski facility instead");
        }
        return skiFacilityRepository.save(createFacility(request));
    }

    private boolean skiFacilityExists(String name) {
        return skiFacilityRepository.existsByName(name);
    }

    @Override
    public SkiFacility createFacility(AddSkiFacilityRequest request) {
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

    @Override
    public SkiFacility getSkiFacilityById(Long id) {
        return skiFacilityRepository.findById(id)
                .orElseThrow(() -> new SkiFacilityNotFoundException("Facility not found"));
    }

    @Override
    public SkiFacilityDto convertToDto(SkiFacility skiFacility) {
        SkiFacilityDto skiFacilityDto = modelMapper.map(skiFacility, SkiFacilityDto.class);
        List<Image> images = imageRepository.findBySkiFacilityId(skiFacility.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        skiFacilityDto.setImages(imageDtos);
        return skiFacilityDto;
    }

    @Override
    public List<SkiFacilityDto> getConvertedSkiFacilities(List<SkiFacility> skiFacilities) {
        return skiFacilities.stream().map(this::convertToDto).toList();
    }
}
