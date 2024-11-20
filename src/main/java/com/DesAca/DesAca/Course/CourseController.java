package com.DesAca.DesAca.Course;

import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DesAca.DesAca.Diagnosis.Diagnosis;
import com.DesAca.DesAca.Diagnosis.DiagnosisService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@RestController
@RequestMapping("/course")
@RequiredArgsConstructor
public class CourseController {

    private final CourseService courseService;
    private final DiagnosisService diagnosisService;
    private static final Logger logger = LoggerFactory.getLogger(CourseController.class);

    @PostMapping
    public ResponseEntity<?> createCourse(@RequestBody @Valid CourseDTO courseDTO) {

        // Validar datos del DTO antes de continuar
        validateCourseDTO(courseDTO);

        try {
            Course savedCourse = courseService.createCourse(courseDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedCourse);
        } catch (Exception e) {
            e.printStackTrace(); // Para logs de depuración
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error al crear el curso: " + e.getMessage());
        }
    }

    // Método para validar los datos del DTO
private void validateCourseDTO(CourseDTO courseDTO) {
    if (courseDTO.getCourseName() == null || courseDTO.getCourseName().isBlank()) {
        throw new IllegalArgumentException("El nombre del curso no puede ser nulo o vacío.");
    }
    if (courseDTO.getDiagnosisId() == null) {
        throw new IllegalArgumentException("El ID del diagnóstico no puede ser nulo.");
    }
    if (courseDTO.getStartDate() == null) {
        throw new IllegalArgumentException("La fecha de inicio no puede ser nula.");
    }
    if (courseDTO.getEndDate() == null) {
        throw new IllegalArgumentException("La fecha de fin no puede ser nula.");
    }
    if (courseDTO.getShift() == null || courseDTO.getShift().isBlank()) {
        throw new IllegalArgumentException("El turno no puede ser nulo o vacío.");
    }
    if (courseDTO.getCapacity() == null || courseDTO.getCapacity() <= 0) {
        throw new IllegalArgumentException("La capacidad debe ser mayor a 0 y no puede ser nula.");
    }
    if (courseDTO.getRequirements() == null || courseDTO.getRequirements().isBlank()) {
        throw new IllegalArgumentException("Los requisitos no pueden ser nulos o vacíos.");
    }
    if (courseDTO.getDepartament() == null || courseDTO.getDepartament().isBlank()) {
        throw new IllegalArgumentException("El departamento no puede ser nulo o vacío.");
    }
}

    // @GetMapping
    // public ResponseEntity<List<Course>>

    // TO DO Modify the endpoint to return the courses not assigned to a professor
    @GetMapping("/not-assigned")
    public ResponseEntity<List<Course>> getCoursesNotAssignedToProfessor() {
        List<Course> courses = courseService.getCoursesNotAssignedToProfessor();
        return ResponseEntity.ok(courses);
    }

}
