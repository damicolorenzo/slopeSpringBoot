package com.lorenzoproject.slope.service.skirun;

import com.lorenzoproject.slope.enums.Status;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.model.SkiFacility;
import com.lorenzoproject.slope.model.SkiRun;
import com.lorenzoproject.slope.repository.SkiFacilityRepository;
import com.lorenzoproject.slope.repository.SkiRunRepository;
import com.lorenzoproject.slope.request.CreateSkiRunRequest;
import com.lorenzoproject.slope.request.UpdateSkiRunRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class SkiRunService implements ISkiRunService{
    private final SkiRunRepository skiRunRepository;
    private final SkiFacilityRepository skiFacilityRepository;

    @Override
    public SkiRun createRun(Long facilityId, CreateSkiRunRequest request) {
        SkiFacility skiFacility = skiFacilityRepository.findById(facilityId)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));
        if(skiFacility.getStatus() == Status.CLOSED) {
            throw new IllegalStateException("Cannot add run to inactive facility");
        }
        SkiRun run = new SkiRun();
        run.setName(request.getName());
        run.setDifficulty(request.getDifficulty());
        run.setLengthKm(request.getLengthKm());
        run.setStatus(Status.OPEN);
        run.setSkiFacility(skiFacility);

        return skiRunRepository.save(run);
    }

    @Override
    public SkiRun updateRun(Long runId, UpdateSkiRunRequest request) {
        SkiRun run = skiRunRepository.findById(runId)
                .orElseThrow(() -> new ResourceNotFoundException("Run not found"));
        if(request.getName() != null)
            run.setName(request.getName());
        if(request.getDifficulty() != null)
            run.setDifficulty(request.getDifficulty());
        if(request.getLengthKm() != null)
            run.setLengthKm(request.getLengthKm());
        if(request.getStatus() != null)
            run.setStatus(request.getStatus());
        return run;
    }

    @Override
    public void deactivateRun(Long runId) {
        SkiRun run = skiRunRepository.findById(runId)
                .orElseThrow(() -> new ResourceNotFoundException("Run not found"));
        run.setStatus(Status.CLOSED);
    }

    @Override
    public List<SkiRun> getRunsByFacility(Long facilityId) {
        return skiRunRepository.findBySkiFacilityIdAndStatus(facilityId, Status.OPEN);
    }

    @Override
    public SkiRun getRun(Long runId) {
        return skiRunRepository.findById(runId)
                .orElseThrow(() -> new ResourceNotFoundException("Run not found"));
    }
}
