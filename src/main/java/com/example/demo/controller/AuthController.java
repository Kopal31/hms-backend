
package com.example.demo.controller;

import com.example.demo.auth.AuthRequest;
import com.example.demo.auth.AuthResponse;
import com.example.demo.model.AppUser;
import com.example.demo.repository.AppUserRepository;
import com.example.demo.security.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin("*")
public class AuthController {

    @Autowired
    private AppUserRepository repository;

    @Autowired
    private BCryptPasswordEncoder encoder;

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private com.example.demo.repository.PatientRepository patientRepository;

    @PostMapping("/signup")
    public String signup(@RequestBody AuthRequest request) {
        if (repository.findByEmail(request.email).isPresent()) {
            return "Email is already registered!";
        }

        AppUser user = new AppUser();
        user.setEmail(request.email);
        user.setPassword(encoder.encode(request.password));
        
        String assignedRole = request.role != null ? request.role : "ROLE_ADMIN";
        user.setRole(assignedRole);
        repository.save(user);

        if ("ROLE_PATIENT".equals(assignedRole)) {
            com.example.demo.model.Patient patient = new com.example.demo.model.Patient();
            patient.setEmail(request.email);
            patient.setName(request.name != null ? request.name : "New Patient");
            patient.setAge(request.age != null ? request.age : 0);
            patient.setGender(request.gender != null ? request.gender : "Other");
            patient.setContact(request.contact != null ? request.contact : "");
            patient.setAddress(request.address != null ? request.address : "");
            patientRepository.save(patient);
        }

        return "User registered successfully";
    }

    @PostMapping("/login")
    public AuthResponse login(@RequestBody AuthRequest request) {

        AppUser user = repository.findByEmail(request.email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (!encoder.matches(request.password, user.getPassword())) {
            throw new RuntimeException("Invalid password");
        }

        String token = jwtUtil.generateToken(user.getEmail(), user.getRole());

        return new AuthResponse(token, user.getRole());
    }
}
