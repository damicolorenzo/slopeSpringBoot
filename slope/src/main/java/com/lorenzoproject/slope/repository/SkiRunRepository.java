package com.lorenzoproject.slope.repository;

import com.lorenzoproject.slope.enums.Status;
import com.lorenzoproject.slope.model.SkiRun;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SkiRunRepository extends JpaRepository<SkiRun, Long> {
    List<SkiRun> findBySkiFacilityIdAndStatus(Long facilityId, Status status);
    boolean existsByName(String name);
}
