package com.lorenzoproject.slope.model;

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
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class SkiFacility {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String type;
    private Status status;

    @OneToMany(mappedBy = "skiFacility")
    private List<LiftStructure> liftStructures;

    @OneToMany(mappedBy = "skiFacility")
    private List<SkiRun> skiRuns;

    @OneToMany(mappedBy = "skiFacility", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Image> images = new ArrayList<>();
}
