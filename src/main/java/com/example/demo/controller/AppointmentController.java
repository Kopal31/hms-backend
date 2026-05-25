package com.example.demo.controller;

import com.example.demo.model.Appointment;
import com.example.demo.service.AppointmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/appointments")
public class AppointmentController {

    @Autowired
    private AppointmentService appointmentService;

    @Autowired
    private com.example.demo.repository.PatientRepository patientRepository;
    @Autowired
    private com.example.demo.repository.AppointmentRepository appointmentRepository;
    @Autowired
    private com.example.demo.repository.DoctorRepository doctorRepository;

    @GetMapping("/my")
    public List<Appointment> getMyAppointments(java.security.Principal principal) {
        if (principal == null) {
            return java.util.Collections.emptyList();
        }
        com.example.demo.model.Patient patient = patientRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));
        return appointmentRepository.findByPatientPatientId(patient.getPatientId());
    }

    @PostMapping("/my")
    public Appointment createMyAppointment(java.security.Principal principal, @NonNull @RequestBody Appointment appointment) {
        if (principal == null) {
            throw new RuntimeException("Unauthorized");
        }
        com.example.demo.model.Patient patient = patientRepository.findByEmail(principal.getName())
                .orElseThrow(() -> new RuntimeException("Patient profile not found"));
        appointment.setPatient(patient);
        
        if (appointment.getDoctor() != null && appointment.getDoctor().getDoctorId() != null) {
            appointment.setDoctor(doctorRepository.findById(appointment.getDoctor().getDoctorId()).orElse(null));
        }
        
        if (appointment.getStatus() == null || appointment.getStatus().isEmpty()) {
            appointment.setStatus("Scheduled");
        }
        return appointmentService.saveAppointment(appointment);
    }

    @GetMapping
    public List<Appointment> getAllAppointments() {
        return appointmentService.getAllAppointments();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Appointment> getAppointmentById( @NonNull @PathVariable Long id) {
        return appointmentService.getAppointmentById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Appointment createAppointment( @NonNull @RequestBody Appointment appointment) {
        return appointmentService.saveAppointment(appointment);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Appointment> updateAppointment(@NonNull @PathVariable Long id, @RequestBody Appointment appointmentDetails) {
        return appointmentService.getAppointmentById(id)
                .map(appointment -> {
                    appointment.setPatient(appointmentDetails.getPatient());
                    appointment.setDoctor(appointmentDetails.getDoctor());
                    appointment.setDate(appointmentDetails.getDate());
                    appointment.setTime(appointmentDetails.getTime());
                    appointment.setStatus(appointmentDetails.getStatus());
                    return ResponseEntity.ok(appointmentService.saveAppointment(appointment));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAppointment(@NonNull @PathVariable Long id) {
        return appointmentService.getAppointmentById(id)
                .map(appointment -> {
                    appointmentService.deleteAppointment(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
