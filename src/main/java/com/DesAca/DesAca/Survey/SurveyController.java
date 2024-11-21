package com.DesAca.DesAca.Survey;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.DesAca.DesAca.Answer.AnswerDTO;
import com.DesAca.DesAca.Course.Course;
import com.DesAca.DesAca.Course.CourseRepository;
import com.DesAca.DesAca.Professor.Professor;
import com.DesAca.DesAca.Professor.ProfessorRepository;
import com.DesAca.DesAca.Question.Question;
import com.DesAca.DesAca.dto.CourseSurveyWithDetailsDTO;

import java.util.List;

@RestController
@RequestMapping("/survey")
public class SurveyController {

    private final SurveyService surveyService;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    public SurveyController(SurveyService surveyService, CourseRepository courseRepository,
            ProfessorRepository professorRepository) {
        this.surveyService = surveyService;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    /**
     * Crear una encuesta.
     * 
     * @param surveyDTO El objeto DTO que contiene los datos de la encuesta.
     * @return La encuesta creada con un código de estado HTTP 201.
     */
    @PostMapping("/create")
    public ResponseEntity<Survey> createSurvey(@RequestBody SurveyDTO surveyDTO) {
        // Verificar curso y profesor a partir de los IDs proporcionados en SurveyDTO
        Course course = courseRepository.findById(surveyDTO.getCourseId())
                .orElseThrow(() -> new RuntimeException("Curso no encontrado"));

        Professor professor = professorRepository.findById(surveyDTO.getProfessorId())
                .orElseThrow(() -> new RuntimeException("Profesor no encontrado"));

        // Crear la encuesta
        Survey survey = surveyService.createSurvey(course, professor);

        return ResponseEntity.status(HttpStatus.CREATED).body(survey);
    }

    @PostMapping("/submit")
    public ResponseEntity<String> submitSurvey(@RequestBody SurveyResponseDTO surveyResponseDTO) {
        surveyService.submitSurvey(surveyResponseDTO);
        return ResponseEntity.ok("Encuesta completada con éxito.");
    }

    @GetMapping("/professor/{professorId}/incomplete-surveys")
    public ResponseEntity<List<Survey>> getIncompleteSurveysByProfessor(@PathVariable Long professorId) {
        List<Survey> incompleteSurveys = surveyService.getIncompleteSurveysByProfessor(professorId);
        return ResponseEntity.ok(incompleteSurveys);
    }

    @GetMapping("/course/{courseId}/details")
    public ResponseEntity<List<CourseSurveyWithDetailsDTO>> getSurveysWithDetailsByCourse(@PathVariable Long courseId) {
        List<CourseSurveyWithDetailsDTO> surveys = surveyService.getSurveysByCourse(courseId);
        return ResponseEntity.ok(surveys);
    }

}
