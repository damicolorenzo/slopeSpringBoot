package com.lorenzoproject.slope.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Blob;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fileName;
    private String fileType;

    @Lob
    private Blob image;
    private String downloadURL;

    @ManyToOne
    @JoinColumn(name = "ski_facility_id")
    @JsonIgnore
    private SkiFacility skiFacility;

    @ManyToOne
    @JoinColumn(name = "ski_run_id")
    @JsonIgnore
    private SkiRun skiRun;

    @ManyToOne
    @JoinColumn(name = "lift_structure_id")
    @JsonIgnore
    private LiftStructure liftStructure;

}
