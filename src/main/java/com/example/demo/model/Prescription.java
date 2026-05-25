package com.example.demo.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Entity
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "prescriptionId", scope = Prescription.class)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Prescription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long prescriptionId;

    private String date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "doctor_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Doctor doctor;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "patient_id")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private Patient patient;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
        name = "prescription_medicine",
        joinColumns = @JoinColumn(name = "prescription_id"),
        inverseJoinColumns = @JoinColumn(name = "medicine_id")
    )
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Medicine> medicines;
}
