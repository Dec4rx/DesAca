package com.DesAca.DesAca.ProfessorCourse;

import java.util.List;

import java.util.Map;
import java.util.HashMap;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.DesAca.DesAca.Course.CourseSummaryDTO;

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
        professorCourseService.createProfessorCourse(
                professorCourseDTO.getProfessorId(),
                professorCourseDTO.getCourseId(),
                professorCourseDTO.isFinished());

        // Crear una respuesta JSON
        Map<String, String> response = new HashMap<>();
        response.put("message", "Relación guardada exitosamente");

        return new ResponseEntity<>(response, HttpStatus.CREATED);
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

}
