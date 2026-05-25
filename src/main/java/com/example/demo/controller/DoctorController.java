package com.example.demo.controller;

import com.example.demo.dto.DoctorDto;
import com.example.demo.model.Doctor;
import com.example.demo.service.DoctorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/doctors")
public class DoctorController {

    @Autowired
    private DoctorService doctorService;

    private DoctorDto toDto(Doctor doctor) {
        return new DoctorDto(
                doctor.getDoctorId(),
                doctor.getName(),
                doctor.getSpecialization(),
                doctor.getEmail(),
                doctor.getPhone()
        );
    }

    @GetMapping
    public List<DoctorDto> getAllDoctors() {
        return doctorService.getAllDoctors().stream()
                .map(this::toDto)
                .collect(Collectors.toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<DoctorDto> getDoctorById(@NonNull @PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(this::toDto)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public DoctorDto createDoctor(@NonNull @RequestBody Doctor doctor) {
        return toDto(doctorService.saveDoctor(doctor));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DoctorDto> updateDoctor(@NonNull @PathVariable Long id, @RequestBody Doctor doctorDetails) {
        return doctorService.getDoctorById(id)
                .map(doctor -> {
                    doctor.setName(doctorDetails.getName());
                    doctor.setSpecialization(doctorDetails.getSpecialization());
                    doctor.setPhone(doctorDetails.getPhone());
                    doctor.setEmail(doctorDetails.getEmail());
                    return toDto(doctorService.saveDoctor(doctor));
                })
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDoctor(@NonNull @PathVariable Long id) {
        return doctorService.getDoctorById(id)
                .map(doctor -> {
                    doctorService.deleteDoctor(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
