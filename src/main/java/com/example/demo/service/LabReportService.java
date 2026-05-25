package com.example.demo.service;

import com.example.demo.model.LabReport;
import com.example.demo.repository.LabReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LabReportService {

    @Autowired
    private LabReportRepository labReportRepository;

    public List<LabReport> getAllLabReports() {
        return labReportRepository.findAll();
    }

    public Optional<LabReport> getLabReportById(@NonNull Long id) {
        return labReportRepository.findById(id);
    }

    public LabReport saveLabReport(@NonNull LabReport labReport) {
        return labReportRepository.save(labReport);
    }

    public void deleteLabReport(@NonNull Long id) {
        labReportRepository.deleteById(id);
    }
}