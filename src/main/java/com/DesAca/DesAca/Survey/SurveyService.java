package com.DesAca.DesAca.Survey;

import org.springframework.stereotype.Service;

import com.DesAca.DesAca.Answer.Answer;
import com.DesAca.DesAca.Answer.AnswerDTO;
import com.DesAca.DesAca.Answer.AnswerRepository;
import com.DesAca.DesAca.Course.Course;
import com.DesAca.DesAca.Course.CourseRepository;
import com.DesAca.DesAca.Professor.Professor;
import com.DesAca.DesAca.Professor.ProfessorRepository;
import com.DesAca.DesAca.Question.Question;
import com.DesAca.DesAca.Question.QuestionRepository;
import com.DesAca.DesAca.dto.CourseSurveyWithDetailsDTO;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SurveyService {

    private final SurveyRepository surveyRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;
    private final CourseRepository courseRepository;
    private final ProfessorRepository professorRepository;

    public SurveyService(SurveyRepository surveyRepository,
            QuestionRepository questionRepository,
            AnswerRepository answerRepository,
            CourseRepository courseRepository,
            ProfessorRepository professorRepository) {
        this.surveyRepository = surveyRepository;
        this.questionRepository = questionRepository;
        this.answerRepository = answerRepository;
        this.courseRepository = courseRepository;
        this.professorRepository = professorRepository;
    }

    public Survey createSurvey(Course course, Professor professor) {
        Survey survey = new Survey();
        survey.setCourse(course);
        survey.setProfessor(professor);
        survey.setComplete(false);
        return surveyRepository.save(survey);
    }

    public List<Question> getAllQuestions() {
        return questionRepository.findAll();
    }

    public void submitSurvey(Long surveyId, List<AnswerDTO> answers) {
        Survey survey = surveyRepository.findById(surveyId)
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada"));

        if (survey.isComplete()) {
            throw new RuntimeException("La encuesta ya fue completada");
        }

        for (AnswerDTO answerDTO : answers) {
            Question question = questionRepository.findById(answerDTO.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));

            Answer answer = new Answer();
            answer.setSurvey(survey);
            answer.setQuestion(question);
            answer.setResponse(answerDTO.getResponse());
            answerRepository.save(answer);
        }

        survey.setComplete(true);
        surveyRepository.save(survey);
    }

    public List<Survey> getSurveysByProfessor(Long professorId) {
        return surveyRepository.findByProfessorId(professorId);
    }

    @Transactional
    public Survey createSurvey(Long courseId, Long professorId) {
        // Verificar curso y profesor
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new IllegalArgumentException("Curso no encontrado"));
        Professor professor = professorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado"));

        // Crear encuesta
        Survey survey = new Survey();
        survey.setCourse(course);
        survey.setProfessor(professor);
        survey.setComplete(false);
        surveyRepository.save(survey);

        // Generar respuestas vacías para todas las preguntas
        List<Question> questions = questionRepository.findAll();
        for (Question question : questions) {
            Answer answer = new Answer();
            answer.setSurvey(survey);
            answer.setQuestion(question);
            answer.setResponse(""); // Respuesta vacía
            answerRepository.save(answer);
        }

        return survey;
    }

    @Transactional
    public void submitSurvey(SurveyResponseDTO surveyResponseDTO) {
        // Buscar la encuesta por su ID
        Survey survey = surveyRepository.findById(surveyResponseDTO.getSurveyId())
                .orElseThrow(() -> new RuntimeException("Encuesta no encontrada"));

        // Verificar si la encuesta ya está completada
        if (survey.isComplete()) {
            throw new RuntimeException("La encuesta ya fue completada.");
        }

        // Guardar las respuestas
        for (AnswerDTO answerDTO : surveyResponseDTO.getAnswers()) {
            Question question = questionRepository.findById(answerDTO.getQuestionId())
                    .orElseThrow(() -> new RuntimeException("Pregunta no encontrada"));

            Answer answer = new Answer();
            answer.setSurvey(survey);
            answer.setQuestion(question);
            answer.setResponse(answerDTO.getResponse());
            answerRepository.save(answer);
        }

        // Marcar la encuesta como completada
        survey.setComplete(true);
        surveyRepository.save(survey);
    }

    public List<Survey> getIncompleteSurveysByProfessor(Long professorId) {
        // Verificar que el profesor existe
        professorRepository.findById(professorId)
                .orElseThrow(() -> new IllegalArgumentException("Profesor no encontrado"));

        // Buscar encuestas incompletas del profesor
        return surveyRepository.findByProfessorIdAndCompleteFalse(professorId);
    }

    public List<CourseSurveyWithDetailsDTO> getSurveysByCourse(Long courseId) {
        List<Survey> surveys = surveyRepository.findByCourseId(courseId);

        List<CourseSurveyWithDetailsDTO> surveyDetails = new ArrayList<>();

        for (Survey survey : surveys) {
            List<AnswerDTO> answers = survey.getAnswers().stream()
                    .map(answer -> new AnswerDTO(answer.getQuestion().getId(), answer.getResponse()))
                    .collect(Collectors.toList());

            surveyDetails.add(new CourseSurveyWithDetailsDTO(
                    survey.getId(),
                    survey.getCourse().getId(),
                    survey.getProfessor().getId(),
                    survey.getProfessor().getName(),
                    answers));
        }

        return surveyDetails;
    }
}
