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
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "medicineId", scope = Medicine.class)
@JsonIgnoreProperties({"hibernateLazyInitializer","handler"})
public class Medicine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long medicineId;

    private String name;
    private int quantity;
    private Double price;

    @JsonIgnore
    @ManyToMany(mappedBy = "medicines")
    @ToString.Exclude
    @EqualsAndHashCode.Exclude
    private List<Prescription> prescriptions;
}
