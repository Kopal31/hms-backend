package com.example.demo.controller;

import com.example.demo.model.Payment;
import com.example.demo.service.PaymentService;

import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/payments")
@CrossOrigin(origins = "http://localhost:3000")
public class PaymentController {
    private final PaymentService paymentService;

    public PaymentController(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @org.springframework.beans.factory.annotation.Autowired
    private com.example.demo.repository.PatientRepository patientRepository;
    @org.springframework.beans.factory.annotation.Autowired
    private com.example.demo.repository.PaymentRepository paymentRepository;

    @GetMapping("/my")
    public List<Payment> getMyPayments(java.security.Principal principal) {
        if (principal == null) {
            return java.util.Collections.emptyList();
        }
        com.example.demo.model.Patient patient = patientRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));
        return paymentRepository.findByBillingPatientPatientId(patient.getPatientId());
    }

    @GetMapping
    public List<Payment> getAllPayments() {
        return paymentService.getAllPayments();
    }

    @GetMapping("/{id}")
    public Payment getPaymentById(@PathVariable @NonNull Long id) {
        return paymentService.getPaymentById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    @PostMapping
    public Payment createPayment(@RequestBody @NonNull Payment payment) {
        return paymentService.savePayment(payment);
    }

    @PutMapping("/{id}")
    public Payment updatePayment(@PathVariable Long id, @RequestBody Payment payment) {
        payment.setPaymentId(id);
        return paymentService.savePayment(payment);
    }

    @DeleteMapping("/{id}")
    public void deletePayment(@PathVariable @NonNull Long id) {
        paymentService.deletePayment(id);
    }
}
