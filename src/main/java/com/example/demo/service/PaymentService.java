package com.example.demo.service;



import com.example.demo.model.Payment;
import com.example.demo.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private com.example.demo.repository.BillingRepository billingRepository;

    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }

    public Optional<Payment> getPaymentById(@NonNull Long id) {
        return paymentRepository.findById(id);
    }

    public Payment savePayment(@NonNull Payment payment) {
        if (payment.getBilling() != null && payment.getBilling().getBillId() != null) {
            com.example.demo.model.Billing billing = billingRepository.findById(payment.getBilling().getBillId()).orElse(null);
            if (billing != null) {
                payment.setBilling(billing);
                if (payment.getAmount() != null && payment.getAmount() >= billing.getTotalAmount()) {
                    billing.setPaymentStatus("Paid");
                    billingRepository.save(billing);
                }
            }
        }
        return paymentRepository.save(payment);
    }

    public void deletePayment(@NonNull Long id) {
        paymentRepository.deleteById(id);
    }
}