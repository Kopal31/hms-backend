package com.example.demo.service;

import com.example.demo.model.Billing;
import com.example.demo.repository.BillingRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillingService {

    @Autowired
    private BillingRepository billingRepository;

    public List<Billing> getAllBillings() {
        return billingRepository.findAll();
    }

    public Optional<Billing> getBillingById(@NonNull Long id) {
        return billingRepository.findById(id);
    }

    public Billing saveBilling(@NonNull Billing billing) {
        return billingRepository.save(billing);
    }

    public void deleteBilling(@NonNull Long id) {
        billingRepository.deleteById(id);
    }
}