package com.lorenzoproject.slope.controller;

import com.lorenzoproject.slope.dto.SkiRunDto;
import com.lorenzoproject.slope.exceptions.AlreadyExistsException;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.exceptions.SkiRunNotFoundException;
import com.lorenzoproject.slope.model.SkiRun;
import com.lorenzoproject.slope.request.AddSkiRunRequest;
import com.lorenzoproject.slope.request.UpdateSkiRunRequest;
import com.lorenzoproject.slope.response.ApiResponse;
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
@RequestMapping("${api.prefix}/skiruns")
public class SkiRunController {
    private final ISkiRunService skiRunService;

    @GetMapping("/skifacility/{skifacilityId}/allskiruns")
    public ResponseEntity<ApiResponse> getAllSkiRunsBySkiFacilityId(@PathVariable("skifacilityId")Long id) {
        List<SkiRun> skiRuns = skiRunService.getRunsByFacility(id);
        List<SkiRunDto> convertedSkiRuns = skiRunService.getConvertedSkiRuns(skiRuns);
        return ResponseEntity.ok(new ApiResponse("Success", convertedSkiRuns));
    }

    @GetMapping("/skirun/{skirunId}/skirun")
    public ResponseEntity<ApiResponse> getSkiRunById(@PathVariable("skirunId") Long id) {
        try {
            SkiRun skiRun = skiRunService.getSkiRunById(id);
            SkiRunDto skiRunDto = skiRunService.convertToDto(skiRun);
            return ResponseEntity.ok(new ApiResponse("Success", skiRunDto));
        } catch(SkiRunNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/add/{skifacilityId}")
    public ResponseEntity<ApiResponse> addSkiRun(@RequestBody AddSkiRunRequest skiRun, @PathVariable("skifacilityId") Long id) {
        try {
            SkiRun theSkiRun = skiRunService.addSkiRun(skiRun, id);
            SkiRunDto skiRunDto = skiRunService.convertToDto(theSkiRun);
            return ResponseEntity.ok(new ApiResponse("Add ski run success", skiRunDto));
        } catch(AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/skirun/{skiRunId}/update")
    public ResponseEntity<ApiResponse> updateSkiRun(@RequestBody UpdateSkiRunRequest request, @PathVariable("skirunId") Long id) {
        try {
            SkiRun theSkiRun = skiRunService.updateSkiRun(request, id);
            SkiRunDto skiRunDto = skiRunService.convertToDto(theSkiRun);
            return ResponseEntity.ok(new ApiResponse("Update ski run success", skiRunDto));
        } catch(ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/skirun/{skirunId}/delete")
    public ResponseEntity<ApiResponse> deleteSkiRun(@PathVariable("skirunId") Long id) {
        try {
            skiRunService.deleteSkiRunById(id);
            return ResponseEntity.ok(new ApiResponse("Delete ski run success", id));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
