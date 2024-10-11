package com.DesAca.DesAca.Diagnosis;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DesAca.DesAca.Course.Course;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@RestController
@RequestMapping(path = "/diagnosis")
@RequiredArgsConstructor
public class DiagnosisController {
    private final DiagnosisService diagnosisService;

    @PostMapping
    public ResponseEntity<Diagnosis> createDiagnosis(@Valid @RequestBody Diagnosis diagnosis) {
        Diagnosis diagnosisCreated = diagnosisService.createDiagnosis(diagnosis);
        if (diagnosisCreated != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(diagnosisCreated);
        }
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
    }

    @GetMapping()
    public Iterable<Diagnosis> getAllDiagnosis() {
        return diagnosisService.getAllDiagnosis();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Diagnosis> getDiagnosisById(@PathVariable Long id) {
        return diagnosisService.getDiagnosisById(id);
    }

    @GetMapping("/authorized")
    public ResponseEntity<List<Diagnosis>> getFullyAuthorizedDiagnoses() {
        return diagnosisService.getFullyAuthorizedDiagnoses();
    }

}
