package com.lorenzoproject.slope.controller;

import com.lorenzoproject.slope.dto.SkiFacilityDto;
import com.lorenzoproject.slope.exceptions.AlreadyExistsException;
import com.lorenzoproject.slope.exceptions.SkiFacilityNotFoundException;
import com.lorenzoproject.slope.model.SkiFacility;
import com.lorenzoproject.slope.request.AddSkiFacilityRequest;
import com.lorenzoproject.slope.request.CreateFacilityRequest;
import com.lorenzoproject.slope.request.UpdateSkiFacilityRequest;
import com.lorenzoproject.slope.response.ApiResponse;
import com.lorenzoproject.slope.service.skifacility.ISkiFacilityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/skifacilities")
public class SkiFacilityController {
    private final ISkiFacilityService skiFacilityService;

    @GetMapping("/all")
    public ResponseEntity<ApiResponse> getAllSkiFacilities() {
        List<SkiFacility> skiFacilities = skiFacilityService.getActiveFacilities();
        List<SkiFacilityDto> convertedSkiFacilities = skiFacilityService.getConvertedSkiFacilities(skiFacilities);
        return ResponseEntity.ok(new ApiResponse("Success", convertedSkiFacilities));
    }

    @GetMapping("/skifacility/{skifacilityId}/skifacility")
    public ResponseEntity<ApiResponse> getSkiFacilityById(@PathVariable("skifacilityId") Long id) {
        try {
            SkiFacility skiFacility = skiFacilityService.getSkiFacilityById(id);
            SkiFacilityDto skiFacilityDto = skiFacilityService.convertToDto(skiFacility);
            return ResponseEntity.ok(new ApiResponse("Success", skiFacilityDto));
        } catch(SkiFacilityNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add")
    public ResponseEntity<ApiResponse> addSkiFacility(AddSkiFacilityRequest skiFacility) {
        try {
            SkiFacility theSkiFacility = skiFacilityService.addSkiFacility(skiFacility);
            SkiFacilityDto skiFacilityDto = skiFacilityService.convertToDto(theSkiFacility);
            return ResponseEntity.ok(new ApiResponse("Add ski facility success", skiFacilityDto));
        } catch(AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    public ResponseEntity<ApiResponse> updateSkiFacility(UpdateSkiFacilityRequest request, Long id) {

    }
}
