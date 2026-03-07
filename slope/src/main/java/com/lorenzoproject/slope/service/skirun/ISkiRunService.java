package com.lorenzoproject.slope.service.skirun;

import com.lorenzoproject.slope.dto.SkiRunDto;
import com.lorenzoproject.slope.model.SkiFacility;
import com.lorenzoproject.slope.model.SkiRun;
import com.lorenzoproject.slope.request.AddSkiRunRequest;
import com.lorenzoproject.slope.request.CreateSkiRunRequest;
import com.lorenzoproject.slope.request.UpdateSkiRunRequest;

import java.math.BigDecimal;
import java.util.List;

public interface ISkiRunService {
    SkiRun addSkiRun(AddSkiRunRequest request, Long facilityId);

    SkiRun createSkiRun(AddSkiRunRequest request, Long facilityId);

    SkiRun updateSkiRun(UpdateSkiRunRequest request, Long id);

    void deleteSkiRunById(Long id);

    void deactivateRun(Long runId);

    List<SkiRun> getRunsByFacility(Long facilityId);

    SkiRun getSkiRunById(Long runId);

    List<SkiRunDto> getConvertedSkiRuns(List<SkiRun> skiRuns);

    SkiRunDto convertToDto(SkiRun skiRun);
}
