package com.DesAca.DesAca.Diagnosis;

import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.time.LocalDate;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
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

    public ResponseEntity<List<Diagnosis>> getFullyAuthorizedDiagnoses() {
        List<Diagnosis> diagnoses = diagnosisRepository.findByIsAuthorizedByFirstTrueAndIsAuthorizedBySecondTrue();
        if (diagnoses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(diagnoses);
    }

    // @Transactional
    public ResponseEntity<DiagnosisDTO> updateDiagnosisPartial(Long id, Map<String, Object> updates) {
        Diagnosis diagnosisToUpdate = diagnosisRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("El diagnostico con ID " + id + " no existe"));
        System.out.println("Diagnostico a actualizar: " + diagnosisToUpdate);

        System.out.println("Actualizaciones: " + updates);
        updates.forEach((key, value) -> {
            if (value == null || (value instanceof String && ((String) value).trim().isEmpty())) {
                throw new RuntimeException("El campo " + key + " no puede ser nulo.");
            }
            switch (key) {
                case "departament": 
                    diagnosisToUpdate.setDepartament((String) value);
                    break;
                case "dateDiagnosis":
                    diagnosisToUpdate.setDateDiagnosis(LocalDate.parse(((String) value).substring(0, 10)));
                    break;
                case "headDepartment":
                    diagnosisToUpdate.setHeadDepartment((String) value);
                    break;
                case "presidentAcademy":
                    diagnosisToUpdate.setPresidentAcademy((String) value);
                    break;
                case "titleSubdirectorate":
                    diagnosisToUpdate.setTitleSubdirectorate((String) value);
                    break;
                case "requiredSubjects":
                    diagnosisToUpdate.setRequiredSubjects((String) value);
                    break;
                case "thematicContents":
                    diagnosisToUpdate.setThematicContents((String) value);
                    break;
                case "numberProfessors":
                    diagnosisToUpdate.setNumberProfessors((Integer) value);
                    break;
                case "typeSubject":
                    diagnosisToUpdate.setTypeSubject((String) value);
                    break;
                case "activityEvent":
                    diagnosisToUpdate.setActivityEvent((String) value);
                    break;
                case "objective":
                    diagnosisToUpdate.setObjective((String) value);
                    break;
                case "careersAttended":
                    diagnosisToUpdate.setCareersAttended((String) value);
                    break;
                case "period":
                    diagnosisToUpdate.setPeriod((String) value);
                    break;
                case "shift":
                    diagnosisToUpdate.setShift((String) value);
                    break;
                case "startDate":
                    diagnosisToUpdate.setStartDate(LocalDate.parse(((String) value).substring(0, 10)));
                    break;
                case "endDate":
                    diagnosisToUpdate.setEndDate(LocalDate.parse(((String) value).substring(0, 10)));
                    break;
                case "status":
                    diagnosisToUpdate.setStatus((String) value);
                    break;
                case "facilitators":
                    diagnosisToUpdate.setFacilitators((String) value);
                    break;
                case "feedback": 
                    System.err.println("Feedback actualizado: " + diagnosisToUpdate.getFeedback());
                    break;
                case "isAuthorizedByFirst":
                    diagnosisToUpdate.setAuthorizedByFirst((Boolean) value);
                    break;
                case "isAuthorizedBySecond":
                    diagnosisToUpdate.setAuthorizedBySecond((Boolean) value);
                    break;
                // Agrega más campos según sea necesario
            }
        });

        try {
            Diagnosis updatedDiagnosis = diagnosisRepository.save(diagnosisToUpdate);
            // Convertir a DTO y devolver
            return ResponseEntity.ok().body(
                    new DiagnosisDTO(
                            updatedDiagnosis.getId(),
                            updatedDiagnosis.getDepartament(),
                            updatedDiagnosis.getDateDiagnosis(),
                            updatedDiagnosis.getHeadDepartment(),
                            updatedDiagnosis.getPresidentAcademy(),
                            updatedDiagnosis.getTitleSubdirectorate(),
                            updatedDiagnosis.getRequiredSubjects(),
                            updatedDiagnosis.getThematicContents(),
                            updatedDiagnosis.getNumberProfessors(),
                            updatedDiagnosis.getTypeSubject(),
                            updatedDiagnosis.getActivityEvent(),
                            updatedDiagnosis.getObjective(),
                            updatedDiagnosis.getCareersAttended(),
                            updatedDiagnosis.getPeriod(),
                            updatedDiagnosis.getShift(),
                            updatedDiagnosis.getStartDate(),
                            updatedDiagnosis.getEndDate(),
                            updatedDiagnosis.getStatus(),
                            updatedDiagnosis.getFacilitators(),
                            updatedDiagnosis.getFeedback(),
                            updatedDiagnosis.isAuthorizedByFirst(),
                            updatedDiagnosis.isAuthorizedBySecond()));

        } catch (DataIntegrityViolationException e) {
            // Maneja errores relacionados con integridad de datos, como restricciones de la
            // base de datos
            System.err.println("Error de integridad de datos al actualizar el diagnostico: " + e.getMessage());
            throw new RuntimeException("Error de integridad de datos al actualizar el diagnostico.", e);

        } catch (Exception e) {
            // Loguea la excepción para más detalles
            System.err.println("Error general al actualizar el diagnostico: " + e.getMessage());
            throw new RuntimeException("Error al actualizar el diagnostico.", e);
        }
    }

}
