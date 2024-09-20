package com.DesAca.DesAca.Professor;

import java.util.Map;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/professor")
@RequiredArgsConstructor
public class ProfessorController {
    private final ProfessorService professorService;

    @PostMapping
    public ResponseEntity<ProfessorDTO> createProfessor(@Valid @RequestBody Professor professor) {
        ProfessorDTO createdProfessor = professorService.createProfessor(professor);
        return new ResponseEntity<>(createdProfessor, HttpStatus.CREATED);
    }

    // Método para actualización parcial usando PATCH
    @PatchMapping("/{id}")
    public ResponseEntity<ProfessorDTO> updateProfessorPartial(@PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        ProfessorDTO updatedProfessor = professorService.updateProfessorPartial(id, updates);
        return ResponseEntity.ok(updatedProfessor);
    }

    // Método para actualizar el status de un profesor
    @PatchMapping("/{id}/status")
    public ResponseEntity<Object> updateProfessorStatus(@PathVariable Long id,
            @RequestBody Map<String, Object> updates) {
        return professorService.updateStatusProfessor(id, updates);
    }

}
