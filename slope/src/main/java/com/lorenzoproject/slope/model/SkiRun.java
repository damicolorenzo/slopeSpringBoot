package com.lorenzoproject.slope.model;

import com.lorenzoproject.slope.enums.DifficultyLevel;
import com.lorenzoproject.slope.enums.Status;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class SkiRun {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private Status status;
    private DifficultyLevel difficulty;
    private Double lengthKm;

    @ManyToOne
    @JoinColumn(name = "ski_facility_id")
    private SkiFacility skiFacility;

    @OneToMany(mappedBy = "skiRun", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
}
