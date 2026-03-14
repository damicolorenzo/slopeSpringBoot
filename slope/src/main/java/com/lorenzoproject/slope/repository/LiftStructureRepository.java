package com.lorenzoproject.slope.repository;

import com.lorenzoproject.slope.enums.Status;
import com.lorenzoproject.slope.model.LiftStructure;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LiftStructureRepository extends JpaRepository<LiftStructure, Long> {
    List<LiftStructure> findBySkiFacilityIdAndStatus(Long id, Status status);
    boolean existsByName(String name);
}
