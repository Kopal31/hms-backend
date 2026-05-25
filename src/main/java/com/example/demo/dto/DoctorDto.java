package com.example.demo.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DoctorDto {
    private Long doctorId;
    private String name;
    private String specialization;
    private String email;
    private String phone;
}
