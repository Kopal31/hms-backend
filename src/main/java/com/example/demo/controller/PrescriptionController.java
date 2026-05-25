package com.example.demo.controller;

import com.example.demo.model.Prescription;
import com.example.demo.service.PrescriptionService;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/prescriptions")
@CrossOrigin(origins = "http://localhost:3000")
public class PrescriptionController {
    private final PrescriptionService prescriptionService;

    public PrescriptionController(PrescriptionService prescriptionService) {
        this.prescriptionService = prescriptionService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    private com.example.demo.repository.PatientRepository patientRepository;
    @org.springframework.beans.factory.annotation.Autowired
    private com.example.demo.repository.PrescriptionRepository prescriptionRepository;

    @GetMapping("/my")
    public List<Prescription> getMyPrescriptions(java.security.Principal principal) {
        if (principal == null) {
            return java.util.Collections.emptyList();
        }
        com.example.demo.model.Patient patient = patientRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));
        return prescriptionRepository.findByPatientPatientId(patient.getPatientId());
    }

    @GetMapping
    public List<Prescription> getAllPrescriptions() {
        return prescriptionService.getAllPrescriptions();
    }

    @GetMapping("/{id}")
    public Prescription getPrescriptionById(@NonNull @PathVariable Long id) {
        return prescriptionService.getPrescriptionById(id).orElseThrow(() -> new RuntimeException("Prescription not found"));
    }

    @PostMapping
    public Prescription createPrescription(@NonNull @RequestBody Prescription prescription) {
        return prescriptionService.savePrescription(prescription);
    }

    @PutMapping("/{id}")
    public Prescription updatePrescription(@PathVariable Long id, @RequestBody Prescription prescription) {
        prescription.setPrescriptionId(id);
        return prescriptionService.savePrescription(prescription);
    }

    @DeleteMapping("/{id}")
    public void deletePrescription(@NonNull @PathVariable Long id) {
        prescriptionService.deletePrescription(id);
    }
}
