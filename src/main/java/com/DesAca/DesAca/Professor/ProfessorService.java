// ProfessorService.java
package com.DesAca.DesAca.Professor;

import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.TransactionSystemException;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ProfessorService {
    private final ProfessorRepository professorRepository;

    public ProfessorDTO createProfessor(Professor professor) {
        // Guardar el profesor en la base de datos
        Professor savedProfessor = professorRepository.save(professor);

        // Convertir a DTO
        return new ProfessorDTO(
            savedProfessor.getId(),
            savedProfessor.getName(),
            savedProfessor.getMiddleName(),
            savedProfessor.getLastName(),
            savedProfessor.getGender(),
            savedProfessor.getStatus()
        );
    }

    @Transactional
public ProfessorDTO updateProfessorPartial(Long id, Map<String, Object> updates) {
    // Buscar el profesor por ID
    Professor existingProfessor = professorRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("El profesor con ID " + id + " no existe"));

    // Aplicar las actualizaciones parciales
    updates.forEach((key, value) -> {
        switch (key) {
            case "name":
                existingProfessor.setName((String) value);
                break;
            case "middleName":
                existingProfessor.setMiddleName((String) value);
                break;
            case "lastName":
                existingProfessor.setLastName((String) value);
                break;
            case "gender":
                existingProfessor.setGender((String) value);
                break;
            case "status":
                existingProfessor.setStatus((String) value);
                break;
            // Agrega más campos según sea necesario
        }
    });

    try {
        // Guardar el profesor actualizado
        Professor updatedProfessor = professorRepository.save(existingProfessor);
        

        // Convertir a DTO y devolver
        return new ProfessorDTO(
            updatedProfessor.getId(),
            updatedProfessor.getName(),
            updatedProfessor.getMiddleName(),
            updatedProfessor.getLastName(),
            updatedProfessor.getGender(),
            updatedProfessor.getStatus()
        );

    } catch (DataIntegrityViolationException e) {
        // Maneja errores relacionados con integridad de datos, como restricciones de la base de datos
        System.err.println("Error de integridad de datos al actualizar el profesor: " + e.getMessage());
        throw new RuntimeException("Error de integridad de datos al actualizar el profesor.", e);

    } catch (TransactionSystemException e) {
        Throwable rootCause = e.getRootCause();
    System.err.println("Error de transacción al actualizar el status: " + rootCause.getMessage());
    throw new RuntimeException("Error de transacción al actualizar el status.", rootCause);


    } catch (Exception e) {
        // Loguea la excepción para más detalles
        System.err.println("Error general al actualizar el profesor: " + e.getMessage());
        throw new RuntimeException("Error al actualizar el profesor.", e);
    }
}


}
