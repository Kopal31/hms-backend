package com.example.demo.service;

import com.example.demo.model.LabTest;
import com.example.demo.repository.LabTestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabTestService {

    @Autowired
    private LabTestRepository labTestRepository;

    public List<LabTest> getAllLabTests() {
        return labTestRepository.findAll();
    }

    public Optional<LabTest> getLabTestById(@NonNull Long id) {
        return labTestRepository.findById(id);
    }

    public LabTest saveLabTest(@NonNull LabTest labTest) {
        return labTestRepository.save(labTest);
    }

    public void deleteLabTest(@NonNull Long id) {
        labTestRepository.deleteById(id);
    }
}