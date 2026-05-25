package com.example.demo.controller;

import com.example.demo.model.LabTest;
import com.example.demo.service.LabTestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/lab-tests")
public class LabTestController {

    @Autowired
    private LabTestService labTestService;

    @GetMapping
    public List<LabTest> getAllLabTests() {
        return labTestService.getAllLabTests();
    }

    @GetMapping("/{id}")
    public ResponseEntity<LabTest> getLabTestById(@NonNull @PathVariable Long id) {
        return labTestService.getLabTestById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public LabTest createLabTest(@NonNull @RequestBody LabTest labTest) {
        return labTestService.saveLabTest(labTest);
    }

    @PutMapping("/{id}")
    public ResponseEntity<LabTest> updateLabTest(@NonNull @PathVariable Long id, @RequestBody LabTest labTestDetails) {
        return labTestService.getLabTestById(id)
                .map(labTest -> {
                    labTest.setTestName(labTestDetails.getTestName());
                    labTest.setTestName(labTestDetails.getTestName());
                    labTest.setCost(labTestDetails.getCost());
                    return ResponseEntity.ok(labTestService.saveLabTest(labTest));
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLabTest(@NonNull @PathVariable Long id) {
        return labTestService.getLabTestById(id)
                .map(labTest -> {
                    labTestService.deleteLabTest(id);
                    return ResponseEntity.ok().<Void>build();
                })
                .orElse(ResponseEntity.notFound().build());
    }
}
