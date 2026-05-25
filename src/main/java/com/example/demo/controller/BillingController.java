package com.example.demo.controller;

import com.example.demo.model.Billing;
import com.example.demo.service.BillingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/billings")
public class BillingController {

    @Autowired
    private BillingService billingService;

    @Autowired
    private com.example.demo.repository.PatientRepository patientRepository;
    @Autowired
    private com.example.demo.repository.BillingRepository billingRepository;

    @GetMapping("/my")
    public List<Billing> getMyBillings(java.security.Principal principal) {
        if (principal == null) {
            return java.util.Collections.emptyList();
        }
        com.example.demo.model.Patient patient = patientRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));
        return billingRepository.findByPatientPatientId(patient.getPatientId());
    }

    @GetMapping
    public List<Billing> getAllBillings() {
        return billingService.getAllBillings();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Billing> getBillingById(@NonNull @PathVariable Long id) {
        return billingService.getBillingById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Billing createBilling(@NonNull @RequestBody Billing billing) {
        return billingService.saveBilling(billing);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Billing> updateBilling(@NonNull @PathVariable Long id, @RequestBody Billing billingDetails) {
        return billingService.getBillingById(id)
                .map(billing -> {
                    billing.setPatient(billingDetails.getPatient());
                    billing.setTotalAmount(billingDetails.getTotalAmount());
                    billing.setPaymentStatus(billingDetails.getPaymentStatus());
                    billing.setDate(billingDetails.getDate());
                    return ResponseEntity.ok(billingService.saveBilling(billing));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBilling(@NonNull @PathVariable Long id) {
        return billingService.getBillingById(id)
                .map(billing -> {
                    billingService.deleteBilling(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
