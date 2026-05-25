package com.example.demo.service;

import com.example.demo.model.Medicine;
import com.example.demo.repository.MedicineRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MedicineService {

    @Autowired
    private MedicineRepository medicineRepository;

    public List<Medicine> getAllMedicines() {
        return medicineRepository.findAll();
    }

    public Optional<Medicine> getMedicineById(@NonNull Long id) {
        return medicineRepository.findById(id);
    }

    public Medicine saveMedicine(@NonNull Medicine medicine) {
        return medicineRepository.save(medicine);
    }

    public void deleteMedicine(@NonNull Long id) {
        medicineRepository.deleteById(id);
    }
}