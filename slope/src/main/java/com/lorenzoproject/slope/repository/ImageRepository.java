package com.lorenzoproject.slope.repository;

import com.lorenzoproject.slope.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ImageRepository extends JpaRepository<Image, Long> {
    List<Image> findBySkiFacilityId(Long id);
    List<Image> findBySkiRunId(Long id);
    List<Image> findByLiftStructureId(Long id);
}
