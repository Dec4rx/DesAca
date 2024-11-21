package com.DesAca.DesAca.dto;

import java.util.List;
import com.DesAca.DesAca.Answer.AnswerDTO;

public class CourseSurveyWithDetailsDTO {
    private Long surveyId;
    private Long courseId;
    private Long professorId;
    private String professorName;
    private List<AnswerDTO> answers;

    // Constructor
    public CourseSurveyWithDetailsDTO(Long surveyId, Long courseId, Long professorId, String professorName, List<AnswerDTO> answers) {
        this.surveyId = surveyId;
        this.courseId = courseId;
        this.professorId = professorId;
        this.professorName = professorName;
        this.answers = answers;
    }

    // Getters y setters
    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public Long getCourseId() {
        return courseId;
    }

    public void setCourseId(Long courseId) {
        this.courseId = courseId;
    }

    public Long getProfessorId() {
        return professorId;
    }

    public void setProfessorId(Long professorId) {
        this.professorId = professorId;
    }

    public String getProfessorName() {
        return professorName;
    }

    public void setProfessorName(String professorName) {
        this.professorName = professorName;
    }

    public List<AnswerDTO> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerDTO> answers) {
        this.answers = answers;
    }
}
