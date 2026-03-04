package com.lorenzoproject.slope.repository;

import com.lorenzoproject.slope.model.Image;

import java.util.List;

public interface ImageRepository {
    List<Image> findBySkiFacilityId(Long id);
    List<Image> findBySkiRunId(Long id);
    List<Image> findByLiftStructureId(Long id);
}
