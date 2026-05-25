package com.example.demo.service;

import com.example.demo.model.Appointment;
import com.example.demo.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public List<Appointment> getAllAppointments() {
        return appointmentRepository.findAll();
    }

    public Optional<Appointment> getAppointmentById(@NonNull Long id) {
        return appointmentRepository.findById(id);
    }

    public Appointment saveAppointment(@NonNull Appointment appointment) {
        return appointmentRepository.save(appointment);
    }

    public void deleteAppointment(@NonNull Long id) {
        appointmentRepository.deleteById(id);
    }
}