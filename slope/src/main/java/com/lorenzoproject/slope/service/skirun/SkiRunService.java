package com.lorenzoproject.slope.service.skirun;

import com.lorenzoproject.slope.dto.ImageDto;
import com.lorenzoproject.slope.dto.SkiFacilityDto;
import com.lorenzoproject.slope.dto.SkiRunDto;
import com.lorenzoproject.slope.enums.Status;
import com.lorenzoproject.slope.exceptions.AlreadyExistsException;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.exceptions.SkiRunNotFoundException;
import com.lorenzoproject.slope.model.Image;
import com.lorenzoproject.slope.model.SkiFacility;
import com.lorenzoproject.slope.model.SkiRun;
import com.lorenzoproject.slope.repository.ImageRepository;
import com.lorenzoproject.slope.repository.SkiFacilityRepository;
import com.lorenzoproject.slope.repository.SkiRunRepository;
import com.lorenzoproject.slope.request.AddSkiRunRequest;
import com.lorenzoproject.slope.request.CreateSkiRunRequest;
import com.lorenzoproject.slope.request.UpdateSkiRunRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.modelmapper.ModelMapper;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SkiRunService implements ISkiRunService{
    private final SkiRunRepository skiRunRepository;
    private final SkiFacilityRepository skiFacilityRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public SkiRun addSkiRun(AddSkiRunRequest request, Long skifacilityId) {
        if(skiRunExists(request.getName())) {
            throw new AlreadyExistsException(request.getName() + " already exists, you may update this ski run instead");
        }
        return skiRunRepository.save(createSkiRun(request, skifacilityId));
    }

    private boolean skiRunExists(String name) {
        return skiRunRepository.existsByName(name);
    }

    @Override
    public SkiRun createSkiRun(AddSkiRunRequest request, Long facilityId) {
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
    public SkiRun updateSkiRun(UpdateSkiRunRequest request, Long runId) {
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
    public void deleteSkiRunById(Long id) {
        skiRunRepository.findById(id).ifPresentOrElse(skiRunRepository::delete,
                () -> {throw new SkiRunNotFoundException("Ski run not found");});
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
    public SkiRun getSkiRunById(Long runId) {
        return skiRunRepository.findById(runId)
                .orElseThrow(() -> new ResourceNotFoundException("Run not found"));
    }

    @Override
    public SkiRunDto convertToDto(SkiRun skiRun) {
        SkiRunDto skiRunDto = modelMapper.map(skiRun, SkiRunDto.class);
        List<Image> images = imageRepository.findBySkiRunId(skiRun.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        skiRunDto.setImages(imageDtos);
        Optional<SkiFacility> skiFacility = skiFacilityRepository.findById(skiRun.getSkiFacility().getId());
        SkiFacilityDto skiFacilityDto = modelMapper.map(skiFacility, SkiFacilityDto.class);
        skiRunDto.setSkiFacility(skiFacilityDto);
        return skiRunDto;
    }

    @Override
    public List<SkiRunDto> getConvertedSkiRuns(List<SkiRun> skiRuns) {
        return skiRuns.stream().map(this::convertToDto).toList();
    }


}
