package com.DesAca.DesAca.Diagnosis;

import org.springframework.stereotype.Service;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DiagnosisService {
    private final DiagnosisRepository diagnosisRepository;

    public Diagnosis createDiagnosis(Diagnosis diagnosis) {
        return diagnosisRepository.save(diagnosis);
    }

    public Diagnosis getDiagnosisById(Long id) {
        return diagnosisRepository.findById(id).orElseThrow();
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
