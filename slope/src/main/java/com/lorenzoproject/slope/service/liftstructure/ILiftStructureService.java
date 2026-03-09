package com.lorenzoproject.slope.service.liftstructure;

import com.lorenzoproject.slope.dto.LiftStructureDto;
import com.lorenzoproject.slope.model.LiftStructure;
import com.lorenzoproject.slope.request.AddLiftStructureRequest;
import com.lorenzoproject.slope.request.UpdateLiftStructureRequest;

import java.util.List;

public interface ILiftStructureService {
    LiftStructure addLiftStructure(AddLiftStructureRequest request, Long id);
    LiftStructure createLiftStructure(AddLiftStructureRequest request, Long id);
    LiftStructure updateLiftStructure(UpdateLiftStructureRequest request, Long id);
    void deleteLiftStructureById(Long id);

    List<LiftStructure> getLiftsByFacility(Long id);
    LiftStructure getLiftStructureById(Long id);
    List<LiftStructureDto> getConvertedLiftStructures(List<LiftStructure> liftStructures);
    LiftStructureDto convertToDto(LiftStructure liftStructure);
}
