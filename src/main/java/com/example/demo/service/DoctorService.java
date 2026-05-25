package com.example.demo.service;

import com.example.demo.model.Doctor;
import com.example.demo.repository.DoctorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DoctorService {

    @Autowired
    private DoctorRepository doctorRepository;

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Optional<Doctor> getDoctorById(@NonNull Long id) {
        return doctorRepository.findById(id);
    }

    public Doctor saveDoctor(@NonNull Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(@NonNull Long id) {
        doctorRepository.deleteById(id);
    }
}