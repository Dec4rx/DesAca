package com.DesAca.DesAca.Diagnosis;

import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;

    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    public ResponseEntity<Diagnosis> getDiagnosisById(Long id) {
        Optional<Diagnosis> diagnosis = diagnosisRepository.findById(id);
        if (diagnosis.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok(diagnosis.get());
    }

    public void deleteDiagnosis(Long id) {
        diagnosisRepository.deleteById(id);
    }

    public Iterable<Diagnosis> getAllDiagnosis() {
        return diagnosisRepository.findAll();
    }

    // public Diagnosis updateDiagnosisPartial(Long id, Diagnosis diagnosis) {
    //     Diagnosis diagnosisToUpdate = diagnosisRepository.findById(id).orElseThrow();
    //     diagnosisToUpdate.setDiagnosis(diagnosis.getDiagnosis());
    //     return diagnosisRepository.save(diagnosisToUpdate);
    // }

}
