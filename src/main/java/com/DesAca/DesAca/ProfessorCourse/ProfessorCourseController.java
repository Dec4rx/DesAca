package com.DesAca.DesAca.ProfessorCourse;

import java.util.List;
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
    public ResponseEntity<String> assignCourseToProfesso(@Valid @RequestBody ProfessorCourseDTO professorCourseDTO) {
        professorCourseService.createProfessorCourse(professorCourseDTO.getProfessorId(),
                professorCourseDTO.getCourseId(), professorCourseDTO.isFinished());
        return new ResponseEntity<>("Relación guardada exitosamente", HttpStatus.CREATED);
    }

    @GetMapping("/{professorId}")
    public ResponseEntity<List<CourseSummaryDTO>> getCoursesByProfessor(@PathVariable Long professorId) {
        List<CourseSummaryDTO> courses = professorCourseService.getCoursesByProfessorId(professorId);
        return ResponseEntity.ok(courses);
    }

    
}
