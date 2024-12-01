package com.DesAca.DesAca.ProfessorCourse;

import java.util.List;

import java.util.Map;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.DesAca.DesAca.Course.CourseSummaryDTO;
import com.DesAca.DesAca.Professor.Professor;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/professor-course")
@RequiredArgsConstructor
public class ProfessorCourseController {
    private final ProfessorCourseService professorCourseService;

    @PostMapping
    public ResponseEntity<Map<String, String>> assignCourseToProfessor(
            @Valid @RequestBody ProfessorCourseDTO professorCourseDTO) {
        Map<String, String> response = new HashMap<>();
        try {
            professorCourseService.createProfessorCourse(
                    professorCourseDTO.getProfessorId(),
                    professorCourseDTO.getCourseId(),
                    professorCourseDTO.isFinished(),
                    professorCourseDTO.getStatusProf());

            response.put("message", "Relación guardada exitosamente");
            return new ResponseEntity<>(response, HttpStatus.CREATED);

        } catch (IllegalStateException | IllegalArgumentException e) {
            response.put("error", e.getMessage());
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            response.put("error", "Error interno del servidor");
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{professorId}")
    public ResponseEntity<List<CourseSummaryDTO>> getCoursesByProfessor(@PathVariable Long professorId) {
        List<CourseSummaryDTO> courses = professorCourseService.getCoursesByProfessorId(professorId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{professorId}/finished")
    public ResponseEntity<List<CourseSummaryDTO>> getFinishedCoursesByProfessor(@PathVariable Long professorId) {
        List<CourseSummaryDTO> courses = professorCourseService.getFinishedCoursesByProfessorId(professorId);
        return ResponseEntity.ok(courses);
    }

    @GetMapping("/{courseId}/professors")
    public ResponseEntity<List<Professor>> getProfessorsByCourseId(@PathVariable Long courseId) {
        List<Professor> professors = professorCourseService.getProfessorsByCourseId(courseId);
        if (professors.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(professors);
    }

    @PutMapping("/{professorCourseId}/attendance")
    public ResponseEntity<ProfessorCourse> updateAttendancePercentage(@PathVariable Long professorCourseId, @RequestParam double percentage) {
        ProfessorCourse updatedProfessorCourse = professorCourseService.updateAttendancePercentage(professorCourseId, percentage);
        return ResponseEntity.ok(updatedProfessorCourse);
    }

    /**
     * Endpoint para generar y descargar una constancia en formato PDF.
     *
     * @param courseId     ID del curso
     * @param professorId  ID del profesor
     * @return PDF de la constancia como respuesta HTTP
     */
    @GetMapping("/certificates/download")
    public ResponseEntity<InputStreamResource> downloadCertificate(
            @RequestParam Long courseId,
            @RequestParam Long professorId) {
        try {
            return professorCourseService.generateAndDownloadCertificate(courseId, professorId);
        } catch (IllegalArgumentException e) {
            // Manejo de errores específicos (datos inválidos)
            return ResponseEntity.badRequest().body(null);
        } catch (IOException e) {
            // Manejo de errores de entrada/salida
            return ResponseEntity.status(500).body(null);
        } catch (Exception e) {
            // Manejo genérico de errores inesperados
            return ResponseEntity.status(500).body(null);
        }
    }
}
