package com.example.demo.repository;



import com.example.demo.model.LabReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LabReportRepository extends JpaRepository<LabReport, Long> {
    List<LabReport> findByPatientPatientId(Long patientId);
}