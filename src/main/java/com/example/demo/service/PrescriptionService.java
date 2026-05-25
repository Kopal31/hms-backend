
package com.example.demo.service;

import com.example.demo.model.Prescription;
import com.example.demo.model.Patient;
import com.example.demo.model.Doctor;
import com.example.demo.model.Medicine;
import com.example.demo.repository.PrescriptionRepository;
import com.example.demo.repository.PatientRepository;
import com.example.demo.repository.DoctorRepository;
import com.example.demo.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class PrescriptionService {

    @Autowired
    private PrescriptionRepository prescriptionRepository;

    @Autowired
    private PatientRepository patientRepository;

    @Autowired
    private DoctorRepository doctorRepository;

    @Autowired
    private MedicineRepository medicineRepository;

    public List<Prescription> getAllPrescriptions() {
        return prescriptionRepository.findAll();
    }

    public Optional<Prescription> getPrescriptionById(@NonNull Long id) {
        return prescriptionRepository.findById(id);
    }

    public Prescription savePrescription(@NonNull Prescription prescription) {
        if (prescription.getPatient() != null && prescription.getPatient().getPatientId() != null) {
            prescription.setPatient(patientRepository.findById(prescription.getPatient().getPatientId()).orElse(null));
        }
        if (prescription.getDoctor() != null && prescription.getDoctor().getDoctorId() != null) {
            prescription.setDoctor(doctorRepository.findById(prescription.getDoctor().getDoctorId()).orElse(null));
        }
        if (prescription.getMedicines() != null) {
            List<Medicine> managedMedicines = prescription.getMedicines().stream()
                .map(m -> m.getMedicineId() != null ? medicineRepository.findById(m.getMedicineId()).orElse(null) : null)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
            prescription.setMedicines(managedMedicines);
        }
        return prescriptionRepository.save(prescription);
    }

    public void deletePrescription(@NonNull Long id) {
        prescriptionRepository.deleteById(id);
    }
}