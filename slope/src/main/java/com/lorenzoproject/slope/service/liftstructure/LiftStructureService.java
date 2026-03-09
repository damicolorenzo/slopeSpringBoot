package com.lorenzoproject.slope.service.liftstructure;

import com.lorenzoproject.slope.dto.ImageDto;
import com.lorenzoproject.slope.dto.LiftStructureDto;
import com.lorenzoproject.slope.enums.Status;
import com.lorenzoproject.slope.exceptions.AlreadyExistsException;
import com.lorenzoproject.slope.exceptions.LiftStructureNotFoundException;
import com.lorenzoproject.slope.exceptions.ResourceNotFoundException;
import com.lorenzoproject.slope.model.Image;
import com.lorenzoproject.slope.model.LiftStructure;
import com.lorenzoproject.slope.model.SkiFacility;
import com.lorenzoproject.slope.model.SkiRun;
import com.lorenzoproject.slope.repository.ImageRepository;
import com.lorenzoproject.slope.repository.LiftStructureRepository;
import com.lorenzoproject.slope.repository.SkiFacilityRepository;
import com.lorenzoproject.slope.request.AddLiftStructureRequest;
import com.lorenzoproject.slope.request.UpdateLiftStructureRequest;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class LiftStructureService implements ILiftStructureService{
    private final LiftStructureRepository liftStructureRepository;
    private final SkiFacilityRepository skiFacilityRepository;
    private final ImageRepository imageRepository;
    private final ModelMapper modelMapper;

    @Override
    public LiftStructure addLiftStructure(AddLiftStructureRequest request, Long id) {
        if(liftStructureExists(request.getName())) {
            throw new AlreadyExistsException(request.getName() + " already exists, you may update this ski run instead");
        }
        return liftStructureRepository.save(createLiftStructure(request, id));
    }

    private boolean liftStructureExists(String name) {
        return liftStructureRepository.existsByName(name);
    }

    @Override
    public LiftStructure createLiftStructure(AddLiftStructureRequest request, Long id) {
        SkiFacility skiFacility = skiFacilityRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Facility not found"));
        if(skiFacility.getStatus() == Status.CLOSED) {
            throw new IllegalStateException("Cannot add lift to inactive facility");
        }
        LiftStructure lift = new LiftStructure();
        lift.setName(request.getName());
        lift.setSeats(request.getSeats());
        lift.setStatus(request.getStatus());
        lift.setType(request.getType());
        lift.setSkiFacility(skiFacility);

        return liftStructureRepository.save(lift);
    }

    @Override
    public LiftStructure updateLiftStructure(UpdateLiftStructureRequest request, Long id) {
        LiftStructure lift = liftStructureRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("lift not found"));
        if(request.getName() != null)
            lift.setName(request.getName());
        if(request.getType() != null)
            lift.setType(request.getType());
        if(request.getStatus() != null)
            lift.setStatus(request.getStatus());
        if(request.getSeats() != null)
            lift.setSeats(request.getSeats());
        return lift;
    }

    @Override
    public void deleteLiftStructureById(Long id) {
        liftStructureRepository.findById(id).ifPresentOrElse(liftStructureRepository::delete,
                () -> {throw new LiftStructureNotFoundException("Lift not found");});
    }

    @Override
    public List<LiftStructure> getLiftsByFacility(Long id) {
        return liftStructureRepository.findBySkiFacilityIdAndStatus(id, Status.OPEN);
    }

    @Override
    public LiftStructure getLiftStructureById(Long runId) {
        return liftStructureRepository.findById(runId)
                .orElseThrow(() -> new ResourceNotFoundException("Lift not found"));
    }

    @Override
    public List<LiftStructureDto> getConvertedLiftStructures(List<LiftStructure> liftStructures) {
        return liftStructures.stream().map(this::convertToDto).toList();
    }

    @Override
    public LiftStructureDto convertToDto(LiftStructure liftStructure) {
        LiftStructureDto liftStructureDto = modelMapper.map(liftStructure, LiftStructureDto.class);
        List<Image> images = imageRepository.findByLiftStructureId(liftStructure.getId());
        List<ImageDto> imageDtos = images.stream()
                .map(image -> modelMapper.map(image, ImageDto.class))
                .toList();
        liftStructureDto.setImages(imageDtos);
        return liftStructureDto;
    }
}
