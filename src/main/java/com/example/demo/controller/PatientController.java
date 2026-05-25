package com.example.demo.controller;

import com.example.demo.model.Patient;
import com.example.demo.service.PatientService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/patients")
public class PatientController {

    @Autowired
    private PatientService patientService;

    @Autowired
    private com.example.demo.repository.PatientRepository patientRepository;

    @GetMapping("/me")
    public ResponseEntity<Patient> getMyPatientProfile(java.security.Principal principal) {
        if (principal == null) {
            return ResponseEntity.status(401).build();
        }
        return patientRepository.findByEmail(principal.getName())
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public List<Patient> getAllPatients() {

        return patientService.getAllPatients();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Patient> getPatientById(
            @PathVariable @NonNull Long id) {

        return patientService.getPatientById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Patient createPatient(
            @RequestBody @NonNull Patient patient) {

        return patientService.savePatient(patient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Patient> updatePatient(
            @PathVariable @NonNull Long id,
            @RequestBody @NonNull Patient patientDetails) {

        return patientService.getPatientById(id)
                .map(patient -> {

                    patient.setName(patientDetails.getName());
                    patient.setAge(patientDetails.getAge());
                    patient.setGender(patientDetails.getGender());
                    patient.setContact(patientDetails.getContact());
                    patient.setAddress(patientDetails.getAddress());

                    return ResponseEntity.ok(
                            patientService.savePatient(patient));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatient(
            @PathVariable @NonNull Long id) {

        return patientService.getPatientById(id)
                .map(patient -> {

                    patientService.deletePatient(id);

                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}