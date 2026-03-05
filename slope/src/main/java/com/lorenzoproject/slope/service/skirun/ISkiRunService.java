package com.lorenzoproject.slope.service.skirun;

import com.lorenzoproject.slope.model.SkiFacility;
import com.lorenzoproject.slope.model.SkiRun;
import com.lorenzoproject.slope.request.CreateSkiRunRequest;
import com.lorenzoproject.slope.request.UpdateSkiRunRequest;

import java.math.BigDecimal;
import java.util.List;

public interface ISkiRunService {
    SkiRun createRun(Long facilityId, CreateSkiRunRequest request);

    SkiRun updateRun(Long runId, UpdateSkiRunRequest request);

    void deactivateRun(Long runId);

    List<SkiRun> getRunsByFacility(Long facilityId);

    SkiRun getRun(Long runId);
}
