package com.lorenzoproject.slope.controller;

import com.lorenzoproject.slope.dto.LiftStructureDto;
import com.lorenzoproject.slope.dto.SkiRunDto;
import com.lorenzoproject.slope.exceptions.AlreadyExistsException;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.exceptions.SkiRunNotFoundException;
import com.lorenzoproject.slope.model.LiftStructure;
import com.lorenzoproject.slope.model.SkiRun;
import com.lorenzoproject.slope.request.AddLiftStructureRequest;
import com.lorenzoproject.slope.request.AddSkiRunRequest;
import com.lorenzoproject.slope.request.UpdateLiftStructureRequest;
import com.lorenzoproject.slope.request.UpdateSkiRunRequest;
import com.lorenzoproject.slope.response.ApiResponse;
import com.lorenzoproject.slope.service.liftstructure.ILiftStructureService;
import com.lorenzoproject.slope.service.skirun.ISkiRunService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CONFLICT;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestController
@RequiredArgsConstructor
@RequestMapping("${api.prefix}/liftstructures")
public class LiftStructureController {
    private final ILiftStructureService liftStructureService;

    @GetMapping("/liftstructure/{skifacilityId}/allliftstructures")
    public ResponseEntity<ApiResponse> getAllLiftStructuresBySkiFacilityId(@PathVariable("skifacilityId")Long id) {
        List<LiftStructure> liftStructures = liftStructureService.getLiftsByFacility(id);
        List<LiftStructureDto> convertedLiftStructures = liftStructureService.getConvertedLiftStructures(liftStructures);
        return ResponseEntity.ok(new ApiResponse("Success", convertedLiftStructures));
    }

    @GetMapping("/liftstructure/{id}/liftstructure")
    public ResponseEntity<ApiResponse> getLiftStructureById(@PathVariable Long id) {
        try {
            LiftStructure liftStructure = liftStructureService.getLiftStructureById(id);
            LiftStructureDto liftStructureDto = liftStructureService.convertToDto(liftStructure);
            return ResponseEntity.ok(new ApiResponse("Success", liftStructureDto));
        } catch(SkiRunNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add/{skifacilityId}")
    public ResponseEntity<ApiResponse> addLiftStructure(@RequestBody AddLiftStructureRequest liftStructure, @PathVariable("skifacilityId") Long id) {
        try {
            LiftStructure theLift = liftStructureService.addLiftStructure(liftStructure, id);
            LiftStructureDto liftStructureDto = liftStructureService.convertToDto(theLift);
            return ResponseEntity.ok(new ApiResponse("Add lift success", liftStructureDto));
        } catch(AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/liftstructure/{id}/update")
    public ResponseEntity<ApiResponse> updateLiftStructure(@RequestBody UpdateLiftStructureRequest request, @PathVariable("liftstructureId") Long id) {
        try {
            LiftStructure theLift = liftStructureService.updateLiftStructure(request, id);
            LiftStructureDto liftStructureDto = liftStructureService.convertToDto(theLift);
            return ResponseEntity.ok(new ApiResponse("Update lift success", liftStructureDto));
        } catch(ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    //@PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/liftstructure/{id}/delete")
    public ResponseEntity<ApiResponse> deleteLiftStructure(@PathVariable Long id) {
        try {
            liftStructureService.deleteLiftStructureById(id);
            return ResponseEntity.ok(new ApiResponse("Delete lift success", id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
