package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "reportId", scope = LabReport.class)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class LabReport {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reportId;

    private String date;
    private String result;

    @ManyToOne(fetch = FetchType.EAGER) // Use EAGER fetch to ensure relations are loaded and serialized
    @JoinColumn(name = "test_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private LabTest labTest;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Patient patient;
}
