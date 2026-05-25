package com.example.demo.controller;

import com.example.demo.model.Medicine;
import com.example.demo.service.MedicineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/medicines")
public class MedicineController {

    @Autowired
    private MedicineService medicineService;

    @GetMapping
    public List<Medicine> getAllMedicines() {
        return medicineService.getAllMedicines();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Medicine> getMedicineById( @NonNull @PathVariable Long id) {
        return medicineService.getMedicineById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Medicine createMedicine( @NonNull @RequestBody Medicine medicine) {
        return medicineService.saveMedicine(medicine);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Medicine> updateMedicine( @NonNull @PathVariable Long id, @RequestBody Medicine medicineDetails) {
        return medicineService.getMedicineById(id)
                .map(medicine -> {
                    medicine.setName(medicineDetails.getName());
                    medicine.setQuantity(medicineDetails.getQuantity());
                    medicine.setPrice(medicineDetails.getPrice());
                    return ResponseEntity.ok(medicineService.saveMedicine(medicine));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMedicine(@NonNull @PathVariable Long id) {
        return medicineService.getMedicineById(id)
                .map(medicine -> {
                    medicineService.deleteMedicine(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
