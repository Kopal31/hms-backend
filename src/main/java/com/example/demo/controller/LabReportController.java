package com.example.demo.controller;

import com.example.demo.model.LabReport;
import com.example.demo.service.LabReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab-reports")
public class LabReportController {

    @Autowired
    private LabReportService labReportService;

    @Autowired
    private com.example.demo.repository.PatientRepository patientRepository;
    @Autowired
    private com.example.demo.repository.LabReportRepository labReportRepository;

    @GetMapping("/my")
    public List<LabReport> getMyLabReports(java.security.Principal principal) {
        if (principal == null) {
            return java.util.Collections.emptyList();
        }
        com.example.demo.model.Patient patient = patientRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));
        return labReportRepository.findByPatientPatientId(patient.getPatientId());
    }

    @GetMapping
    public List<LabReport> getAllLabReports() {
        return labReportService.getAllLabReports();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabReport> getLabReportById(@NonNull @PathVariable Long id) {
        return labReportService.getLabReportById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public LabReport createLabReport(@NonNull @RequestBody LabReport labReport) {
        return labReportService.saveLabReport(labReport);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabReport> updateLabReport(@NonNull @PathVariable Long id, @RequestBody LabReport labReportDetails) {
        return labReportService.getLabReportById(id)
                .map(labReport -> {
                    labReport.setPatient(labReportDetails.getPatient());
                    labReport.setLabTest(labReportDetails.getLabTest());
                    labReport.setResult(labReportDetails.getResult());
                    labReport.setDate(labReportDetails.getDate());
                    return ResponseEntity.ok(labReportService.saveLabReport(labReport));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabReport(@NonNull @PathVariable Long id) {
        return labReportService.getLabReportById(id)
                .map(labReport -> {
                    labReportService.deleteLabReport(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
