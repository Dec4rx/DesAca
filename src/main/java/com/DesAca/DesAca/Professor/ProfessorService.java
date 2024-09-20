// ProfessorService.java
package com.DesAca.DesAca.Professor;

import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
                savedProfessor.getStatus());
    }

    // @Transactional
    public ResponseEntity<Object> updateStatusProfessor(long id, Map<String, Object> updates) {
        try {
            // Buscar el profesor por ID
            Professor existingProfessor = professorRepository.findById(id)
                    .orElseThrow(() -> new RuntimeException("El profesor con ID " + id + " no existe"));

            // Verificar si el campo 'status' está presente y no está vacío
            if (!updates.containsKey("status")) {
                return ResponseEntity.badRequest().body(Map.of("message", "El campo 'status' es obligatorio."));
            }

            String status = (String) updates.get("status");
            if (status == null || status.isBlank()) {
                return ResponseEntity.badRequest().body(Map.of("message", "El campo 'status' no puede estar vacío."));
            }

            // Actualizar el campo 'status'
            existingProfessor.setStatus(status);

            // Guardar el profesor actualizado en la base de datos
            Professor updatedProfessor = professorRepository.save(existingProfessor);

            // Retornar el objeto actualizado como respuesta
            return ResponseEntity.ok().body(new ProfessorDTO(
                    updatedProfessor.getId(),
                    updatedProfessor.getName(),
                    updatedProfessor.getMiddleName(),
                    updatedProfessor.getLastName(),
                    updatedProfessor.getGender(),
                    updatedProfessor.getStatus()));

        } catch (DataIntegrityViolationException e) {
            // Manejo de errores de integridad de datos
            System.err.println("Error de integridad de datos: " + e.getMessage());
            return ResponseEntity.badRequest()
                    .body(Map.of("message", "Error de integridad de datos. Verifica las restricciones."));
        } catch (TransactionSystemException e) {
            // Manejo específico del error de transacción
            Throwable rootCause = e.getRootCause();
            System.err.println("Error de transacción: " + rootCause.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al confirmar la transacción.", "details", rootCause.getMessage()));
        } catch (Exception e) {
            // Loguea cualquier otro error no capturado específicamente
            System.err.println("Error al actualizar el profesor: " + e.getMessage());
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("message", "Error al actualizar el profesor.", "details", e.getMessage()));
        }
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
                    updatedProfessor.getStatus());

        } catch (DataIntegrityViolationException e) {
            // Maneja errores relacionados con integridad de datos, como restricciones de la
            // base de datos
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
